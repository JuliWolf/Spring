package com.example.spring._9.AOP_Pointcut_Declarations.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyDemoLoggingAspect {

  @Pointcut("execution(* com.example.spring._9.AOP_Pointcut_Declarations.dao.*.*(..)))")
  private void forDaoPackage () {}

  // create pointcut for getter methods
  @Pointcut("execution(* com.example.spring._9.AOP_Pointcut_Declarations.dao.*.get*(..)))")
  private void getter() {}

  // create pointcut for setter methods
  @Pointcut("execution(* com.example.spring._9.AOP_Pointcut_Declarations.dao.*.set*(..)))")
  private void setter() {}

  // create pointcut: include package ... exclude getter/setter
  @Pointcut("forDaoPackage() && !(getter() || setter())")
  private void forDaoPackageNoGetterSetter () {}

  @Before("forDaoPackageNoGetterSetter()")
  public void beforeAddAccountAdvice () {
    System.out.println("\n======>>> Executing @Before advice on method()");
  }

  @Before("forDaoPackageNoGetterSetter()")
  public void performApiAnalytics () {
    System.out.println("\n======>>> Performing API analytics");
  }

  @Before("forDaoPackageNoGetterSetter()")
  public void logToCloudAsync () {
    System.out.println("\n======>>> Logging to Cloud in async fashion");
  }
}
