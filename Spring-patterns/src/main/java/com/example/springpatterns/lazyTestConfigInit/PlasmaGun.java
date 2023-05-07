package com.example.springpatterns.lazyTestConfigInit;

import org.springframework.stereotype.Component;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
@Component
public class PlasmaGun {
  public PlasmaGun() {
    System.out.println("Plazma arrived");
  }

  public void shoot (Commando commando) {
    if (commando instanceof Billy) {
      return;
    }

    commando.setAlive(false);
  }
}
