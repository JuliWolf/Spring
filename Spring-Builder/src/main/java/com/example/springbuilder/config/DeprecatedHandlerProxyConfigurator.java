package com.example.springbuilder.config;

import java.lang.reflect.InvocationHandler;
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
      return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), new InvocationHandler() {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
          System.out.println("********** чтож ты делаешь урод!!! ");
          // Вызываем метод у оригинального объекта
          return method.invoke(t);
        }
      });
    }

    return t;
  }
}
