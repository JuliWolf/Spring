package com.example.spring._15.AOP_around.aspect;

import com.example.spring._15.AOP_around.Account;
import com.example.spring._15.AOP_around.MainDemoApp;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

  private Logger logger = Logger.getLogger(MainDemoApp.class.getName());

  @Around("execution(* com.example.spring._15.AOP_around.service.*.getFortune(..))")
  public Object aroundGetFortune (ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

    // print out method we are advising on
    String method = proceedingJoinPoint.getSignature().toShortString();
    logger.info("\n====>> Executing @Around on method: " + method);

    // get begin timestamp
    long begin = System.currentTimeMillis();

    // now let's execute the method
    Object result = null;

    // handle exception
//    try {
//      result = proceedingJoinPoint.proceed();

//    // get end timestamp
//    long end = System.currentTimeMillis();
//
//    // compute duration and display it
//    long duration = end - begin;
//    logger.info("\n====> Duration: " + duration / 1000.0 + " seconds");
//
//    return result;
//    } catch (Exception ex) {
//      // log the exception
//      logger.warning(ex.getMessage());
//
//      // give user a custom message
//      result = "Major accident! But no worries, your private AOP helicopter is on the way!";
//    }

    // rethrow exception
    try {
      result = proceedingJoinPoint.proceed();

      return result;
    } catch (Exception ex) {
      // log the exception
      logger.warning(ex.getMessage());

      // rethrow exception
      throw ex;
    }
  }

  @After("execution(* com.example.spring._15.AOP_around.dao.AccountDAO.findAccounts(..))")
  public void afterFinallyFindAccountAdvice (JoinPoint joinPoint) {
    // print out which method we are advising on
    String method = joinPoint.getSignature().toShortString();
    logger.info("\n====>> Executing @After (finally) on method: " + method);
  }

  @AfterThrowing(
      pointcut = "execution(* com.example.spring._15.AOP_around.dao.AccountDAO.findAccounts(..))",
      throwing = "exc"
  )
  public void afterThrowingFindAccountsAdvice (JoinPoint joinPoint, Throwable exc) {
    // print out which method we are advising on
    String method = joinPoint.getSignature().toShortString();
    logger.info("\n====>> Executing @AfterThrowing on method: " + method);

    // log the exception
    logger.info("\n====>> Exception is: " + exc);
  }

  // add new advice for @AfterReturning on the findAccount method
  @AfterReturning(
      pointcut = "execution(* com.example.spring._15.AOP_around.dao.AccountDAO.findAccounts(..))",
      returning = "result"
  )
  public void afterReturningFindAccountsAdvice (JoinPoint joinPoint, List<Account> result) {
    // print out which method we are advising on
    String method = joinPoint.getSignature().toShortString();
    logger.info("\n====>> Executing @AfterReturning on method: " + method);

    // print out the results of the method call
    logger.info("\n====>> result is: " + result);

    // post-process the data and modify it

     // convert the account names to uppercase
    convertAccountNamesToUpperCase(result);
  }

  @Before("com.example.spring._15.AOP_around.aspect.AopExpressions.forDaoPackageNoGetterSetter()")
  public void beforeAddAccountAdvice (JoinPoint joinPoint) {
    logger.info("\n======>>> Executing @Before advice on method()");

    // display the method signature
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

    // display method arguments
    logger.info("Method: " + methodSignature);

    // get args
    Object[] args = joinPoint.getArgs();

    // loop through args
    for (Object arg: args) {
      logger.info(arg.toString());

      if (arg instanceof Account) {
        // downcast and print Account specific stuff
        Account theAccount = (Account) arg;

        logger.info("account name: " + theAccount.getName());
        logger.info("account level: " + theAccount.getLevel());
      }
    }
  }

  private void convertAccountNamesToUpperCase(List<Account> result) {
    // loop through accounts
    for (Account account: result) {
      // get uppercase version of name
      String upperName = account.getName().toUpperCase();

      // update the name on the account
      account.setName(upperName);
    }
  }
}
