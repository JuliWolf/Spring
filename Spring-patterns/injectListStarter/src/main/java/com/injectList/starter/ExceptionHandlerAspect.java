package com.injectList.starter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author JuliWolf
 * @date 09.05.2023
 */

public class ExceptionHandlerAspect implements MethodInterceptor {
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    try {
      // Вызыв настоящего метода
      return invocation.proceed();
    } catch (Throwable ex) {
      System.out.println("PSR е работает!!!");
      throw ex;
    }
  }
}
