package com.example.spring._15.AOP_around.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AopExpressions {
  @Pointcut("execution(* com.example.spring._15.AOP_around.dao.*.*(..)))")
  public void forDaoPackage () {}

  // create pointcut for getter methods
  @Pointcut("execution(* com.example.spring._15.AOP_around.dao.*.get*(..)))")
  public void getter() {}

  // create pointcut for setter methods
  @Pointcut("execution(* com.example.spring._15.AOP_around.dao.*.set*(..)))")
  public void setter() {}

  // create pointcut: include package ... exclude getter/setter
  @Pointcut("forDaoPackage() && !(getter() || setter())")
  public void forDaoPackageNoGetterSetter () {}
}
