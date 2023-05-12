package com.example.springbuilder.config;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author JuliWolf
 * @date 12.05.2023
 */
public class DeprecatedHandlerProxyConfigurator implements ProxyConfigurator {
  @Override
  public Object replaceWithProxyIfNeeded(Object t, Class implClass) {
    if (implClass.isAnnotationPresent(Deprecated.class)) {
      if (implClass.getInterfaces().length == 0) {
        System.out.println(implClass.getSimpleName());
        return Enhancer.create(implClass, (net.sf.cglib.proxy.InvocationHandler) (proxy, method, args) -> getInvocationHandlerLogic(t, method, args));
      }

      return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), (proxy, method, args) -> getInvocationHandlerLogic(t, method, args));
    }

    return t;
  }

  private Object getInvocationHandlerLogic(Object t, Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
    System.out.println("********** чтож ты делаешь урод!!! ");
    // Вызываем метод у оригинального объекта
    return method.invoke(t, args);
  }
}
