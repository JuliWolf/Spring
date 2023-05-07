package com.example.springpatterns.customComponentAutowiredAnnotation;

import com.example.springpatterns.customComponentAutowiredAnnotation.annotations.Treatment;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
@Treatment(type = Лечение.АЛКОГОЛЬ)
public class Аспирин implements Лечение {
  @Override
  public void применить(Patient patient) {
    System.out.println("Одну таблетку запись водой после еды");
  }
}
