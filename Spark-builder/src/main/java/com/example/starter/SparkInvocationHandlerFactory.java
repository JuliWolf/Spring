package com.example.starter;

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

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    return null;
  }
}
