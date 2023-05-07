package com.example.springpatterns.lazyTestConfigInit;

import lombok.Getter;
import lombok.Setter;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
@Getter
@Setter
public abstract class Commando {
  private boolean alive;

      public Commando () {
        System.out.println(getClass().getSimpleName() + " was created");
      }
}
