package com.example.springbuilder;

import com.example.springbuilder.annotations.Singleton;
import com.example.springbuilder.config.Config;
import com.example.springbuilder.factory.ObjectFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JuliWolf
 * @date 11.05.2023
 */
public class ApplicationContext {
  @Setter
  private ObjectFactory factory;
  private Map<Class, Object> cache = new ConcurrentHashMap<>();

  @Getter
  private Config config;

  public ApplicationContext(Config config) {
    this.config = config;
  }


  public <T> T getObject (Class<T> type) {
    Class<? extends T> implClass = type;

    if (cache.containsKey(type)) {
      return (T) cache.get(type);
    }

    if (type.isInterface()) {
      implClass = config.getImpClass(type);
    }

    T t = factory.createObject(implClass);

    if (implClass.isAnnotationPresent(Singleton.class)) {
      cache.put(type, t);
    }

    return t;
  }
}
