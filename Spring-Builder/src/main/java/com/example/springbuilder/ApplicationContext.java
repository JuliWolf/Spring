package com.example.springbuilder;

import com.example.springbuilder.factory.ObjectFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author JuliWolf
 * @date 11.05.2023
 */
public class ApplicationContext {
  private ObjectFactory factory;
  private Map<Class, Object> cache = new ConcurrentHashMap<>();

  public <T> T getObject (Class<T> type) {
    return null;
  }
}
