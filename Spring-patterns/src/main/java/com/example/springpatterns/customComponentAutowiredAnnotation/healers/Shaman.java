package com.example.springpatterns.customComponentAutowiredAnnotation.healers;

import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;
import org.springframework.stereotype.Component;

/**
 * @author JuliWolf
 * @date 08.05.2023
 */
@Component
public class Shaman implements Healer {
  @Override
  public void treat(Patient patient) {
    System.out.println("Shaman says:");
    System.out.println("бей в бубен громче!");
  }

  @Override
  public String myType() {
    return "магия";
  }
}
