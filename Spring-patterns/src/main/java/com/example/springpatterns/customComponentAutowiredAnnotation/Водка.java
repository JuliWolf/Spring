package com.example.springpatterns.customComponentAutowiredAnnotation;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
public class Водка implements Лечение{
  @Override
  public void применить(Patient patient) {
    System.out.println("Настоять 50 мл водки и выпить за час до сна");
  }
}
