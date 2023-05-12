package com.example.springbuilder.factory;

import com.example.springbuilder.ApplicationContext;
import com.example.springbuilder.config.Config;
import com.example.springbuilder.config.JavaConfig;
import com.example.springbuilder.config.ObjectConfigurator;
import com.example.springbuilder.models.AngryPolicemanImpl;
import com.example.springbuilder.models.Policeman;
import lombok.Setter;
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
  private final ApplicationContext context;
  private List<ObjectConfigurator> configurators = new ArrayList<>();


  @SneakyThrows
  public ObjectFactory (ApplicationContext context) {
    this.context = context;
    for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
      configurators.add(aClass.getDeclaredConstructor().newInstance());
    }
  }

  @SneakyThrows
  public <T> T createObject (Class<T> implClass) {

    T t = implClass.getDeclaredConstructor().newInstance();

    configurators.forEach(objectConfigurator -> objectConfigurator.configure(t, context));

    return t;
  }
}
