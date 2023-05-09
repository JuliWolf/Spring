package com.example.springpatterns.customComponentAutowiredAnnotation;

import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;
import com.example.springpatterns.customComponentAutowiredAnnotation.healers.Healer;

/**
 * @author JuliWolf
 * @date 08.05.2023
 */
public interface Hospital {
  void register (String type, Healer healer);

  void processPatient (Patient patient);
}
