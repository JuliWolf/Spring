package com.example.springbuilder.factory;

import com.example.springbuilder.config.Config;
import com.example.springbuilder.config.JavaConfig;
import com.example.springbuilder.config.ObjectConfigurator;
import com.example.springbuilder.models.AngryPolicemanImpl;
import com.example.springbuilder.models.Policeman;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * @author JuliWolf
 * @date 10.05.2023
 */
public class ObjectFactory {
  private Config config;
  private List<ObjectConfigurator> configurators = new ArrayList<>();

  private static ObjectFactory ourInstance = new ObjectFactory();


  public static ObjectFactory getInstance() {
    return ourInstance;
  }

  @SneakyThrows
  private ObjectFactory () {
    config = new JavaConfig("com.example", new HashMap<>(Map.of(Policeman.class, AngryPolicemanImpl.class)));
    for (Class<? extends ObjectConfigurator> aClass : config.getScanner().getSubTypesOf(ObjectConfigurator.class)) {
      configurators.add(aClass.getDeclaredConstructor().newInstance());
    }
  }

  @SneakyThrows
  public <T> T createObject (Class<T> type) {
    Class<? extends T> implClass = type;

    if (type.isInterface()) {
      implClass = config.getImpClass(type);
    }
    T t = implClass.getDeclaredConstructor().newInstance();

    configurators.forEach(objectConfigurator -> objectConfigurator.configure(t));

    return t;
  }
}
