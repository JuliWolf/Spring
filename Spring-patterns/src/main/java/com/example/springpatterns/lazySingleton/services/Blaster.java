package com.example.springpatterns.lazySingleton.services;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
@Service
public class Blaster {
  @PostConstruct
  private void init () {
    System.out.println("you paid 100500 for the blaster");
  }

  public void fire () {
    System.out.println("Boom Boom !!!");
  }
}
