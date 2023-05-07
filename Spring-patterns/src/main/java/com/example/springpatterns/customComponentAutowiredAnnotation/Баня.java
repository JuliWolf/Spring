package com.example.springpatterns.customComponentAutowiredAnnotation;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
public class Баня implements Лечение{
  @Override
  public void применить(Patient patient) {
    System.out.println("три захода в баню по 10 минут при температуре больше 90 градусов");
  }
}
