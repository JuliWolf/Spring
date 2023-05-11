package com.example.springbuilder.factory;

import com.example.springbuilder.annotations.InjectProperty;
import com.example.springbuilder.models.AngryPolicemanImpl;
import com.example.springbuilder.models.Policeman;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * @author JuliWolf
 * @date 10.05.2023
 */
public class ObjectFactory {
  private Config config;

  private static ObjectFactory ourInstance = new ObjectFactory();

  public static ObjectFactory getInstance() {
    return ourInstance;
  }

  private ObjectFactory () {
    config = new JavaConfig("com.example", new HashMap<>(Map.of(Policeman.class, AngryPolicemanImpl.class)));
  }

  @SneakyThrows
  public <T> T createObject (Class<T> type) {
    Class<? extends T> implClass = type;

    if (type.isInterface()) {
      implClass = config.getImpClass(type);
    }
    T t = implClass.getDeclaredConstructor().newInstance();

    for (Field field : implClass.getDeclaredFields()) {
      InjectProperty annotation = field.getAnnotation(InjectProperty.class);
      String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
      Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
      Map<String, String> propertiesMap = lines
          .map(line -> line.split("="))
          .collect(toMap(arr -> arr[0], arr -> arr[1]));

      String value;
      if ( annotation != null) {
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

    return t;
  }
}
