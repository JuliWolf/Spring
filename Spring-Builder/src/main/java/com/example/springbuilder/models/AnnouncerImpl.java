package com.example.springbuilder.models;

/**
 * @author JuliWolf
 * @date 10.05.2023
 */
public class AnnouncerImpl implements Announcer {
  @Override
  public void announce(String message) {
    System.out.println(message);
  }
}
