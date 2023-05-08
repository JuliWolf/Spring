package com.example.springpatterns.customComponentAutowiredAnnotation.healers;

import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;
import com.example.springpatterns.customComponentAutowiredAnnotation.treatments.Treatment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * @author JuliWolf
 * @date 08.05.2023
 */
@Component
public class DefaultHealer implements Healer {
//  @Autowired
//  private Random random;

  @Autowired
  private List<Treatment> treatments;

  @Override
  public void treat(Patient patient) {
//    System.out.println("Welcome to Default healer");
//    treatments.get(random.nextInt(treatments.size())).use(patient);
//    System.out.println();
    System.out.println("Само пройдет");
  }

  @Override
  public String myType() {
    return "default";
  }
}
