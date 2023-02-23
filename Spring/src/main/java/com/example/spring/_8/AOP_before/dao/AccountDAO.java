package com.example.spring._8.AOP_before.dao;

import com.example.spring._8.AOP_before.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDAO {

  public void addAccount (Account account, boolean vipFlag) {
    System.out.println(getClass() + ": DOING MY DB WORD: ADDING AN ACCOUNT");
  }

  public boolean doWork () {
    System.out.println(getClass() + ": doWork()");

    return false;
  }
}
