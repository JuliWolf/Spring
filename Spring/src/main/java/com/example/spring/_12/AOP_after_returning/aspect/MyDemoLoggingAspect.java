package com.example.spring._12.AOP_after_returning.aspect;

import com.example.spring._12.AOP_after_returning.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

  // add new advice for @AfterReturning on the findAccount method
  @AfterReturning(
      pointcut = "execution(* com.example.spring._12.AOP_after_returning.dao.AccountDAO.findAccounts(..))",
      returning = "result"
  )
  public void afterReturningFindAccountsAdvice (JoinPoint joinPoint, List<Account> result) {
    // print out which method we are advising on
    String method = joinPoint.getSignature().toShortString();
    System.out.println("\n====>> Executing @AfterReturning on method: " + method);

    // print out the results of the method call
    System.out.println("\n====>> result is: " + result);

    // post-process the data and modify it

     // convert the account names to uppercase
    convertAccountNamesToUpperCase(result);
  }

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
