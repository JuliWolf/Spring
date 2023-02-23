package com.example.spring._11.AOP_arguments.aspect;

import com.example.spring._11.AOP_arguments.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {
  @Before("AopExpressions.forDaoPackageNoGetterSetter()")
  public void beforeAddAccountAdvice (JoinPoint joinPoint) {
    System.out.println("\n======>>> Executing @Before advice on method()");

    // display the method signature
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

    // display method arguments
    System.out.println("Method: " + methodSignature);

    // get args
    Object[] args = joinPoint.getArgs();

    // loop through args
    for (Object arg: args) {
      System.out.println(arg);

      if (arg instanceof Account) {
        // downcast and print Account specific stuff
        Account theAccount = (Account) arg;

        System.out.println("account name: " + theAccount.getName());
        System.out.println("account level: " + theAccount.getLevel());
      }
    }
  }


}
