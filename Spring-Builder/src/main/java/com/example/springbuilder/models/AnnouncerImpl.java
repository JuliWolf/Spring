package com.example.springbuilder.models;

import com.example.springbuilder.annotations.InjectByType;
import com.example.springbuilder.factory.ObjectFactory;

/**
 * @author JuliWolf
 * @date 10.05.2023
 */
public class AnnouncerImpl implements Announcer {
  @InjectByType
  private Recommendator recommendator;
  @Override
  public void announce(String message) {
    System.out.println(message);
    recommendator.recommend();
  }
}
