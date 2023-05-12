package com.example.springbuilder.config;

import com.example.springbuilder.ApplicationContext;

/**
 * @author JuliWolf
 * @date 11.05.2023
 */
public interface ObjectConfigurator {
  void configure (Object t, ApplicationContext context);
}
