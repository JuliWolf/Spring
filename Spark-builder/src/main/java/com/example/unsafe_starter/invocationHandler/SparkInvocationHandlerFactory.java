package com.example.unsafe_starter.invocationHandler;

import com.example.unsafe_starter.DataExtractorResolver;
import com.example.unsafe_starter.SparkRepository;
import com.example.unsafe_starter.filterTransformation.SparkTransformation;
import com.example.unsafe_starter.postFinalizer.LazyCollectionInjectorPostFinalizer;
import com.example.unsafe_starter.utils.WordsMatcher;
import com.example.unsafe_starter.annotations.Source;
import com.example.unsafe_starter.annotations.Transient;
import com.example.unsafe_starter.dataExtractor.DataExtractor;
import com.example.unsafe_starter.finalizer.Finalizer;
import com.example.unsafe_starter.transformationSpider.TransformationSpider;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JuliWolf
 * @date 14.05.2023
 */
@Component
@RequiredArgsConstructor
public class SparkInvocationHandlerFactory {

  private final DataExtractorResolver dataExtractorResolver;
  private final Map<String, TransformationSpider> spiderMap;
  private final Map<String, Finalizer> finalizerMap;

  @Setter
  private ConfigurableApplicationContext realContext;

  public SparkInvocationHandler create (Class<? extends SparkRepository> sparkRepoInterface) {
    // Получаем название класса
    Class<?> modelClass = getModelClass(sparkRepoInterface);
    // Получаем из аннотации Source значение value, которое будет являться путем до файла
    String pathToData = modelClass.getAnnotation(Source.class).value();
    // получаем все названия полей из модели
    Set<String> fieldNames = getFieldNames(modelClass);
    // Создаем DataExtractor
    DataExtractor dataExtractor = dataExtractorResolver.resolve(pathToData);

    // будет хранить в себе имплементации "финальных" методов
    Map<Method, Finalizer> method2Finalizer = new HashMap<>();
    // будет хранить в себе список методов трансформаций
    // Список таплов где трансформация прикреплена к листу колонок, которые данная трансформация будет использовать
    Map<Method, List<Tuple2<SparkTransformation, List<String>>>> transformationChain = new HashMap<>();

    Method[] methods = sparkRepoInterface.getMethods();
    /* method -> List<User> findByNameOfGrandmotherContainsAndAgeLessThanOrderByAgeAndNameSave
    * field names -> NameOfGrandmother, Age, Age
    * Strategy name -> findBy, OrderBy
    * FilterTransformation -> Contains, LessThan
    * Finalizer -> Save
    */
    for (Method method : methods) {
      TransformationSpider currentSpider = null;
      String name = method.getName();
      /* findByNameOfGrandmotherContainsAndAgeLessThanOrderByAgeAndNameSave превратится
      * {"find", "by", "of", "grandmother", "contains" ..etc}
      */
      List<String> methodWords = WordsMatcher.toWordsByJavaConvention(name);
      List<Tuple2<SparkTransformation, List<String>>> transformations = new ArrayList<>();

      while (methodWords.size() > 1) {
        // 1й список -> название стратегий таких как findBy, OrderBy
        // 2й список -> список слов, на который было распаршено имя метода
        String spiderName = WordsMatcher
            .findAndRemoveMatchingPiecesIfExists(spiderMap.keySet(), methodWords);
        if (!spiderName.isEmpty()) {
          currentSpider = spiderMap.get(spiderName);
        }

        transformations.add(currentSpider.getTransformation(methodWords, fieldNames));
      }

      transformationChain.put(method, transformations);
      String finalizerName = "collect";
      if (methodWords.size() == 1) {
        finalizerName = Introspector.decapitalize(methodWords.get(0));
      }
      method2Finalizer.put(method, finalizerMap.get(finalizerName));
    }

    return SparkInvocationHandler.builder()
        .modelClass(modelClass)
        .pathToData(pathToData)
        .dataExtractor(dataExtractor)
        .transformationChain(transformationChain)
        .finalizerMap(method2Finalizer)
        .postFinalizer(new LazyCollectionInjectorPostFinalizer(realContext))
        .context(realContext)
        .build();
  }

  private Class<?> getModelClass(Class<? extends SparkRepository> repoInterface) {
    // Берем первый параметр интерфейса и кастим его в `ParameterizedType`
    ParameterizedType genericInterface = (ParameterizedType) repoInterface.getGenericInterfaces()[0];
    // Получаем аргументы
    Class<?> modelClass = (Class<?>) genericInterface.getActualTypeArguments()[0];
    return modelClass;
  }

  private Set<String> getFieldNames(Class<?> modelClass) {
    // Получаем все свойства класса
    return Arrays.stream(modelClass.getDeclaredFields())
        // Отфильтровываем все, которые имеют аннотацию @Transient
        .filter(field -> !field.isAnnotationPresent(Transient.class))
        // Отфильтровываем все свойтсва, которые являются коллекциями
        .filter(field -> !Collection.class.isAssignableFrom(field.getType()))
        // Получаем название филдов
        .map(Field::getName)
        // Собираем все в set чтобы хранить только уникальные значения
        .collect(Collectors.toSet());

  }
}
