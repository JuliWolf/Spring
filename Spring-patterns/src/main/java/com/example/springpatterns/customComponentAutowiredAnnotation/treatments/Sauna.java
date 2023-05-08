package com.example.springpatterns.customComponentAutowiredAnnotation.treatments;

import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
public class Sauna implements Treatment {
  @Override
  public void use(Patient patient) {
    System.out.println("три захода в баню по 10 минут при температуре больше 90 градусов");
  }
}
