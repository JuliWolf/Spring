package com.example.starter;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.beans.Introspector;
import java.lang.reflect.Proxy;
import java.util.Set;

/**
 * @author JuliWolf
 * @date 14.05.2023
 */
public class SparkApplicationContextInitializer  implements ApplicationContextInitializer {
  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    registerSparkBean(applicationContext);

    // Получаем значение из файла окружения
    String packagesToScan = applicationContext.getEnvironment().getProperty("spark.packages-to-scan");
    // Создаем класс для сканирования пакетов
    Reflections scanner = new Reflections(packagesToScan);
    // Получаем все классы, которые реализуют интерфейс `SparkRepository`
    Set<Class<? extends SparkRepository>> sparkRepoInterfaces = scanner.getSubTypesOf(SparkRepository.class);
    // Итерируемся по всем имплементациям
    sparkRepoInterfaces.forEach(sparkRepoInterface -> {
      // Создаем прокси для каждой имплементации
      Object golem = Proxy.newProxyInstance(
          sparkRepoInterface.getClassLoader(),
          new Class[]{sparkRepoInterface},
          invocationHandler
      );

      // регистрируем бин
      applicationContext
          .getBeanFactory()
          .registerSingleton(
              Introspector.decapitalize(sparkRepoInterface.getSimpleName()), golem
          );
    });
  }

  private void registerSparkBean(ConfigurableApplicationContext applicationContext) {
    // Вытаскиваем из `application.properties` название приложения
    String appName = applicationContext.getEnvironment().getProperty("spark.app-name");
    // Создаем бины
    SparkSession sparkSession = SparkSession.builder().appName(appName).master("local[*]").getOrCreate();
    JavaSparkContext sparkContext = new JavaSparkContext(sparkSession.sparkContext());
    // Регистрируем бины
    applicationContext.getBeanFactory().registerSingleton("sparkContext", sparkContext);
    applicationContext.getBeanFactory().registerSingleton("sparkSession", sparkSession);
  }
}
