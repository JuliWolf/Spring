package com.example.springpatterns.customComponentAutowiredAnnotation.treatments;

import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;
import com.example.springpatterns.customComponentAutowiredAnnotation.annotations.TreatmentType;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
@TreatmentType(type = Treatment.ALCOHOL)
public class Aspirin implements Treatment {
  @Override
  public void use(Patient patient) {
    System.out.println("Одну таблетку запить водой после еды");
  }
}
