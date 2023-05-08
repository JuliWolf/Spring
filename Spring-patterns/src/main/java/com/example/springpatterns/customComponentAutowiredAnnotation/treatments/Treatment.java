package com.example.springpatterns.customComponentAutowiredAnnotation.treatments;

import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
public interface Treatment {
  String ALCOHOL = "alcohol";
  void use(Patient patient);
}
