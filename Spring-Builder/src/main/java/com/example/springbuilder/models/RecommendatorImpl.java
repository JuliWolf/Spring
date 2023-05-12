package com.example.springbuilder.models;

import com.example.springbuilder.annotations.InjectProperty;
import com.example.springbuilder.annotations.Singleton;

/**
 * @author JuliWolf
 * @date 10.05.2023
 */
@Singleton
public class RecommendatorImpl implements Recommendator {
  @InjectProperty("wisky")
  private String alcohol;

  public RecommendatorImpl() {
    System.out.println("recommendator was created");
  }

  @Override
  public void recommend() {
    System.out.println("to protect from covid-2019 drink " + alcohol);
  }
}
