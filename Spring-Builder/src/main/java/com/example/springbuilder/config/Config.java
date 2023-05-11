package com.example.springbuilder.config;

import org.reflections.Reflections;

/**
 * @author JuliWolf
 * @date 10.05.2023
 */
public interface Config {
  <T> Class<? extends T> getImpClass(Class<T> type);

  Reflections getScanner();
}
