package com.example.springpatterns.customComponentAutowiredAnnotation.healers;

import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
public interface Healer {
  void treat (Patient patient);

  String myType();

  String TRADITIONAL = "traditional";
  String FOLK = "folk";
  String ALCOHOL = "alcohol";
}
