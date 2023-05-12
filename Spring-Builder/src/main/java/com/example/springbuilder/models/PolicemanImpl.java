package com.example.springbuilder.models;

import com.example.springbuilder.annotations.InjectByType;


/**
 * @author JuliWolf
 * @date 10.05.2023
 */
public class PolicemanImpl implements Policeman {

  @InjectByType
  private Recommendator recommendator;

  public void init () {
    System.out.println(recommendator.getClass());
  }

  @Override
  public void makePeopleLeaveRoom() {
    System.out.println("пиф паф, бах бах, кыш кыш");
  }
}
