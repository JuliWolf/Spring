package com.example.unsafe_starter.postFinalizer;

import com.example.sparkbuilder.ForeignKey;
import com.example.unsafe_starter.LazySparkList;
import com.example.unsafe_starter.annotations.Source;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.lang.reflect.ParameterizedType;

/**
 * @author JuliWolf
 * @date 27.05.2023
 */
@RequiredArgsConstructor
public class LazyCollectionInjectorPostFinalizer implements PostFinalizer {
  private final ConfigurableApplicationContext realContext;

  @SneakyThrows
  @Override
  public Object postFinalize(Object retVal) {
    // Если не является коллекцией то возвращаем в исходном виде
    if (!Collection.class.isAssignableFrom(retVal.getClass())) {
      return retVal;
    }

    List models = (List) retVal;
    for (Object model : models) {
      Field idField = model.getClass().getDeclaredField("id");
      idField.setAccessible(true);
      Long ownerId = idField.getLong(model);

      // Получаем все филды класса
      Field[] fields = model.getClass().getDeclaredFields();
      for (Field field : fields) {
        // Проверяем тип филда
        if (List.class.isAssignableFrom(field.getType())) {
          // Получаем бин LazySparkList (придет запроксированный)
          LazySparkList sparkList = realContext.getBean(LazySparkList.class);
          sparkList.setOwnerId(ownerId);

          // Пытаемся получить аннотацию ForeignKey
          String columnName = field.getAnnotation(ForeignKey.class).value();
          sparkList.setForeignKeyName(columnName);

          // Получаем класс связанного листа
          Class<?> embeddedModel = getEmbeddedModel(field);
          sparkList.setModelClass(embeddedModel);

          // Получаем путь до данных
          String pathToData = embeddedModel.getAnnotation(Source.class).value();
          sparkList.setPathToSource(pathToData);

          field.setAccessible(true);
          field.set(model,sparkList);
        }
      }
    }

    return null;
  }

  private Class<?> getEmbeddedModel(Field field) {
    ParameterizedType genericType = (ParameterizedType) field.getGenericType();
    Class<?> embeddedModel = (Class<?>) genericType.getActualTypeArguments()[0];
    return embeddedModel;
  }
}
