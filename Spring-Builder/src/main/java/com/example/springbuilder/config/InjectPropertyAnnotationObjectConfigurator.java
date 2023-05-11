package com.example.springbuilder.config;

import com.example.springbuilder.annotations.InjectProperty;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * @author JuliWolf
 * @date 11.05.2023
 */
public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {
  private Map<String, String> propertiesMap;

  @SneakyThrows
  public InjectPropertyAnnotationObjectConfigurator () {
    String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
    Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
    propertiesMap = lines
        .map(line -> line.split("="))
        .collect(toMap(arr -> arr[0], arr -> arr[1]));
  }

  @Override
  @SneakyThrows
  public void configure(Object t) {
    Class<?> implClass = t.getClass();
    
    for (Field field : implClass.getDeclaredFields()) {
      InjectProperty annotation = field.getAnnotation(InjectProperty.class);
      if ( annotation != null) {
        String value;
        if (annotation.value().isEmpty()) {
          value = propertiesMap.get(field.getName());
        } else {
          value = propertiesMap.get(annotation.value());
        }

        field.setAccessible(true);
        // t указывается для того, чтобы засетить пропертю к конкретному объекту
        field.set(t, value);
      }
    }
  }
}
