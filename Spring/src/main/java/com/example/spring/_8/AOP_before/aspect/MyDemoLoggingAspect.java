package com.example.spring._8.AOP_before.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyDemoLoggingAspect {

//  @Before("execution(public void updateAccount())") // specific method name
//  @Before("execution(public void com.example.spring._8.AOP_before.dao.AccountDAO.addAccount())") // method from exact class
//  @Before("execution(public void add*())") // any public method starts with `add`
//  @Before("execution(void add*())") // any method starts with `add`
//  @Before("execution(* add*(com.example.spring._8.AOP_before.Account))")
//  @Before("execution(* add*(Account))") // error
//  @Before("execution(* add*(com.example.spring._8.AOP_before.Account, ..))") // Account anf any number of arguments
//  @Before("execution(* add*(..))") // any parameter
  @Before("execution(* com.example.spring._8.AOP_before.dao.*.*(..)))") // all methods on package
  public void beforeAddAccountAdvice () {
    System.out.println("\n======>>> Executing @Before advice on method()");
  }

  // this is where we add all of our related advices for logging
}
