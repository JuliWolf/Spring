package com.example.springbuilder.config;

/**
 * @author JuliWolf
 * @date 12.05.2023
 */
public interface ProxyConfigurator {
  Object replaceWithProxyIfNeeded(Object t, Class implClass);
}
