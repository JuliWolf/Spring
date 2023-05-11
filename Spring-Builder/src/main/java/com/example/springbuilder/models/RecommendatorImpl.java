package com.example.springbuilder.models;

import com.example.springbuilder.annotations.InjectProperty;

/**
 * @author JuliWolf
 * @date 10.05.2023
 */
public class RecommendatorImpl implements Recommendator {
  @InjectProperty("wisky")
  private String alcohol;

  @Override
  public void recommend() {
    System.out.println("to protect from covid-2019 drink " + alcohol);
  }
}
