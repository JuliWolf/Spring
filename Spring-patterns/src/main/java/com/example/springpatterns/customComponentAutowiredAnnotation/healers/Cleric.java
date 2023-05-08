package com.example.springpatterns.customComponentAutowiredAnnotation.healers;

import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;
import com.example.springpatterns.customComponentAutowiredAnnotation.treatments.Treatment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author JuliWolf
 * @date 08.05.2023
 */
@Component
public class Cleric implements Healer {
  @Autowired
  List<Treatment> oldMethods;

  @Override
  public void treat(Patient patient) {
    System.out.println("Cleric says:");
    oldMethods.forEach(treatment -> treatment.use(patient));
  }

  @Override
  public String myType() {
    return FOLK;
  }
}
