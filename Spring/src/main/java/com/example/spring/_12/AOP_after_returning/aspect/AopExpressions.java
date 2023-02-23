package com.example.spring._12.AOP_after_returning.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AopExpressions {
  @Pointcut("execution(* com.example.spring._12.AOP_after_returning.dao.*.*(..)))")
  public void forDaoPackage () {}

  // create pointcut for getter methods
  @Pointcut("execution(* com.example.spring._12.AOP_after_returning.dao.*.get*(..)))")
  public void getter() {}

  // create pointcut for setter methods
  @Pointcut("execution(* com.example.spring._12.AOP_after_returning.dao.*.set*(..)))")
  public void setter() {}

  // create pointcut: include package ... exclude getter/setter
  @Pointcut("forDaoPackage() && !(getter() || setter())")
  public void forDaoPackageNoGetterSetter () {}
}
