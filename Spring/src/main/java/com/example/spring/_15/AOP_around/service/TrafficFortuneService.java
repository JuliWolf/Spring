package com.example.spring._15.AOP_around.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TrafficFortuneService {

  public String getFortune () {
    try {
      // simulate a delay
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException exception) {
      exception.printStackTrace();
    }

    // return a fortune
    return "Expect heavy traffic this morning";
  }

  public String getFortune (boolean tripWire) {
    if (tripWire) {
      throw new RuntimeException("Major accident! Highway is closed!");
    }

    // return a fortune
    return getFortune();
  }
}
