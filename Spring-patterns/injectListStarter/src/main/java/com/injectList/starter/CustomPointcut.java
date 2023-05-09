package com.injectList.starter;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * @author JuliWolf
 * @date 09.05.2023
 */
public class CustomPointcut extends DynamicMethodMatcherPointcut {
  // можно передать через пропсы при использовании пакета
  // @Value
  // через configurationProperties
  private String packagesToHandle = "com.example.springpatterns.dynamic_aop.psr";

  @Override
  public boolean matches(Method method, Class<?> targetClass, Object... args) {
    // Определяет для каких методов нужно вызывать
    return true;
  }

  @Override
  public ClassFilter getClassFilter() {
    // методы вызывается 1 раз, чтобы отфильтровать классы для проксирования
    return new ClassFilter() {
      @Override
      public boolean matches(Class<?> aClass) {
        return aClass.getPackage().getName().contains(packagesToHandle);
      }
    };
  }
}
