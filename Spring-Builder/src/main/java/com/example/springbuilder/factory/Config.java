package com.example.springbuilder.factory;

/**
 * @author JuliWolf
 * @date 10.05.2023
 */
public interface Config {
  <T> Class<? extends T> getImpClass(Class<T> type);
}
