package com.example.starter;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JuliWolf
 * @date 14.05.2023
 */
public class SparkInvocationHandlerFactory {

  private DataExtractorResolver dataExtractorResolver;
  private Map<String, TransformationSpider> spiderMap;
  private Map<String, Finalizer> finalizerMap;
  private ConfigurableApplicationContext context;

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
    Map<Method, List<SparkTransformation>> transformationChain = new HashMap<>();

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
      List<SparkTransformation> transformations = new ArrayList<>();

      while (methodWords.size() > 1) {
        // 1й список -> название стратегий таких как findBy, OrderBy
        // 2й список -> список слов, на который было распаршено имя метода
        String spiderName = WordsMatcher
            .findAndRemoveMatchingPiecesIfExists(spiderMap.keySet(), methodWords);
        if (!spiderName.isEmpty()) {
          currentSpider = spiderMap.get(spiderName);
        }

        transformations.add(currentSpider.getTransformation(methods));
      }

      transformationChain.put(method, transformations);
      String finalizerName = "collect";
      if (methodWords.size() == 1) {
        finalizerName = methodWords.get(0);
      }
      method2Finalizer.put(method, finalizerMap.get(finalizerName));
    }

    return SparkInvocationHandler.builder()
        .modelClass(modelClass)
        .pathToData(pathToData)
        .dataExtractor(dataExtractor)
        .transformationChain(transformationChain)
        .finalizerMap(method2Finalizer)
        .context(context)
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
