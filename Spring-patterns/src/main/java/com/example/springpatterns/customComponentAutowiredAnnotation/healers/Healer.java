package com.example.springpatterns.customComponentAutowiredAnnotation.healers;

import com.example.springpatterns.customComponentAutowiredAnnotation.Hospital;
import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
public interface Healer {
  void treat (Patient patient);

  String myType();

  @Autowired
  default void regMe(Hospital hospital) {
    hospital.register(myType(), this);
  }

  String TRADITIONAL = "traditional";
  String FOLK = "folk";
  String ALCOHOL = "alcohol";
}
