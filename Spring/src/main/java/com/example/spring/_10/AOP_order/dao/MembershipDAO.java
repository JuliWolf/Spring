package com.example.spring._10.AOP_order.dao;

import org.springframework.stereotype.Component;

@Component
public class MembershipDAO {
  public boolean addSillyMember () {
    System.out.println(getClass() + ": DOING STUFF: ADDING A MEMBERSHIP ACCOUNT");

    return true;
  }

  public void goToSleep () {
    System.out.println(getClass() + ": goToSleep()");
  }
}
