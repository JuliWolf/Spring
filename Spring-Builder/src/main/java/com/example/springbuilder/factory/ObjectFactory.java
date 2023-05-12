package com.example.springbuilder.factory;

import com.example.springbuilder.ApplicationContext;
import com.example.springbuilder.config.Config;
import com.example.springbuilder.config.JavaConfig;
import com.example.springbuilder.config.ObjectConfigurator;
import com.example.springbuilder.config.ProxyConfigurator;
import com.example.springbuilder.models.AngryPolicemanImpl;
import com.example.springbuilder.models.Policeman;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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
  private List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();


  @SneakyThrows
  public ObjectFactory (ApplicationContext context) {
    this.context = context;
    for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
      configurators.add(aClass.getDeclaredConstructor().newInstance());
    }

    for (Class<? extends ProxyConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ProxyConfigurator.class)) {
      proxyConfigurators.add(aClass.getDeclaredConstructor().newInstance());
    }
  }

  @SneakyThrows
  public <T> T createObject (Class<T> implClass) {
    T t = create(implClass);

    configure(t);

    invokeInit(implClass, t);

    t = wrapWithProxyIfNeeded(implClass, t);

    return t;
  }

  private <T> T wrapWithProxyIfNeeded(Class<T> implClass, T t) {
    for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
      t = (T) proxyConfigurator.replaceWithProxyIfNeeded(t, implClass);
    }
    return t;
  }

  private <T> void invokeInit(Class<T> implClass, T t) throws IllegalAccessException, InvocationTargetException {
    for (Method method : implClass.getMethods()) {
      if (method.isAnnotationPresent(PostConstruct.class)) {
        method.invoke(t);
      }
    }
  }

  private <T> void configure(T t) {
    configurators.forEach(objectConfigurator -> objectConfigurator.configure(t, context));
  }

  private <T> T create(Class<T> implClass) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    T t = implClass.getDeclaredConstructor().newInstance();
    return t;
  }
}
