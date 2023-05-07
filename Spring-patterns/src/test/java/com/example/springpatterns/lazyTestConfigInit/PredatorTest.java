package com.example.springpatterns.lazyTestConfigInit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
@SpringBootTest(classes = MockConfigurationLazy.class)
public class PredatorTest {

  @Autowired
  private Billy billy;

  @Autowired
  private Predator predator;

  @Test
  public void predatorKillNotDatch () {
    predator.fight(billy);
    Assertions.assertFalse(billy.isAlive());
  }
}
