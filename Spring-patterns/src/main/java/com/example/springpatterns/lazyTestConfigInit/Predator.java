package com.example.springpatterns.lazyTestConfigInit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
@Service
public class Predator {
  @Autowired
  private PlasmaGun lazer;

  public Predator () {
    System.out.println("predator was created");
  }

  public void fight (Commando commando) {
    lazer.shoot(commando);
  }
}
