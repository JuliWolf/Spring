package com.example.spring._15.AOP_around.dao;

import com.example.spring._15.AOP_around.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountDAO {

  private String name;
  private String serviceCode;

  // add a new method: findAccounts()

  public List<Account> findAccounts (boolean tripWire) {
    // for academic purpose .. simulate an exception
    if (tripWire) {
      throw new RuntimeException("No soup for you!!!");
    }

    List<Account> accounts = new ArrayList<>();

    // create sample accounts
    Account johnAccount = new Account("John", "Silver");
    Account mikeAccount = new Account("Mike", "Platinum");
    Account mashaAccount = new Account("Masha", "Gold");

    // add them to the list
    accounts.add(johnAccount);
    accounts.add(mikeAccount);
    accounts.add(mashaAccount);

    return accounts;
  }

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
