package com.example.spring._10.AOP_order.dao;

import com.example.spring._10.AOP_order.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDAO {

  private String name;
  private String serviceCode;

  public String getName() {
    System.out.println(getClass() + ": in getName()");
    return name;
  }

  public void setName(String name) {
    System.out.println(getClass() + ": in setName()");
    this.name = name;
  }

  public String getServiceCode() {
    System.out.println(getClass() + ": in getServiceCode()");
    return serviceCode;
  }

  public void setServiceCode(String serviceCode) {
    System.out.println(getClass() + ": in setServiceCode()");
    this.serviceCode = serviceCode;
  }

  public void addAccount (Account account, boolean vipFlag) {
    System.out.println(getClass() + ": DOING MY DB WORD: ADDING AN ACCOUNT");
  }

  public boolean doWork () {
    System.out.println(getClass() + ": doWork()");

    return false;
  }
}
