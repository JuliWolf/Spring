package com.example.springbuilder.models;

import com.example.springbuilder.factory.ObjectFactory;

/**
 * @author JuliWolf
 * @date 10.05.2023
 */
public class AnnouncerImpl implements Announcer {
  private Recommendator recommendator = ObjectFactory.getInstance().createObject(Recommendator.class);
  @Override
  public void announce(String message) {
    System.out.println(message);
    recommendator.recommend();
  }
}
