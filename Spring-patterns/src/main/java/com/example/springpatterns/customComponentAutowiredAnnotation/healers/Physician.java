package com.example.springpatterns.customComponentAutowiredAnnotation.healers;

import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;
import com.example.springpatterns.customComponentAutowiredAnnotation.treatments.Treatment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author JuliWolf
 * @date 08.05.2023
 */
@Component
public class Physician implements Healer{
  @Autowired
  private Treatment aspirin;

  @Override
  public void treat(Patient patient) {
    System.out.println("Physician says:");
    aspirin.use(patient);
  }

  @Override
  public String myType() {
    return TRADITIONAL;
  }
}
