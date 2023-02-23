package com.example.spring._14.AOP_after;

import com.example.spring._14.AOP_after.dao.AccountDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainDemoApp {
  public static void main(String[] args) {
    // read spring config java class
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig.class);

    // get the bean from spring container
    AccountDAO accountDAO = context.getBean("accountDAO", AccountDAO.class);

    List<Account> accounts = null;

    try {
      // add boolean flag to simulate exceptions
      boolean tripWire = true;

      // *** @After goes after @AfterThrowing

      accounts = accountDAO.findAccounts(tripWire);
    } catch (Exception exception) {
      System.out.println("\n\nMain Program: ... caught exception: " + exception);
    }

    // display the accounts
    System.out.println("\n\nMain Program: AfterThrowingDemoApp");
    System.out.println("----");

    System.out.println(accounts);

    System.out.println("\n");
    // close the context
    context.close();
  }
}
