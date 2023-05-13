package com.example.starter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author JuliWolf
 * @date 13.05.2023
 */
public class SparkInvocationHandlerFactory implements InvocationHandler {
  // Класс модели (1)
  private Class<?> modelClass;

  // Ссылка на данные для данной модели(1)
  private String pathToData;

  // Класс для извлечения данных(1)
  private DataExtractor dataExtractor;

  // Трансформации (у каждого метода свой список)
  private Map<Method, List<SparkTransformation>> transformationChain;

  // Терминальная операция (у каждого метода свой список)
  private Map<Method, Finalizer> finalizerMap;

  private ConfigurableApplicationContext context;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Dataset<Row> dataset = dataExtractor.load(pathToData, context);
    List<SparkTransformation> transformations = transformationChain.get(method);

    for (SparkTransformation transformation : transformations) {
      dataset = transformation.transform(dataset);
    }

    Finalizer finalizer = finalizerMap.get(method);

    Object retVal = finalizer.doAction(dataset);
    return retVal;
  }
}
