package com.example.springpatterns.customComponentAutowiredAnnotation.treatments;

import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
public class Cognac implements Treatment {
  @Override
  public void use(Patient patient) {
    System.out.println("Настоять 50 мл водки и выпить за час до сна");
  }
}
