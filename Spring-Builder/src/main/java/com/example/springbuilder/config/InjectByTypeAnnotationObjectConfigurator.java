package com.example.springbuilder.config;

import com.example.springbuilder.ApplicationContext;
import com.example.springbuilder.annotations.InjectByType;
import com.example.springbuilder.factory.ObjectFactory;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

/**
 * @author JuliWolf
 * @date 11.05.2023
 */
public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {
  @SneakyThrows
  @Override
  public void configure(Object t, ApplicationContext context) {
    for (Field field : t.getClass().getDeclaredFields()) {
      if (field.isAnnotationPresent(InjectByType.class)) {
        field.setAccessible(true);
        Object object = context.getObject(field.getType());
        field.set(t, object);
      }
    }
  }
}
