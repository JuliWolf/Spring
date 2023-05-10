package com.example.springbuilder.factory;

import com.example.springbuilder.models.AngryPolicemanImpl;
import com.example.springbuilder.models.Policeman;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

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

    // todo

    return t;
  }
}
