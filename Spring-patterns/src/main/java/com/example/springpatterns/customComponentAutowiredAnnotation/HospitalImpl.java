package com.example.springpatterns.customComponentAutowiredAnnotation;

import com.example.springpatterns.customComponentAutowiredAnnotation.Hospital;
import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;
import com.example.springpatterns.customComponentAutowiredAnnotation.healers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author JuliWolf
 * @date 08.05.2023
 */
//@Service
public class HospitalImpl implements Hospital {
  @Autowired
  private Cleric cleric;

  @Autowired
  private AlcoDoctor alcoDoctor;

  @Autowired
  private DefaultHealer defaultHealer;

  @Autowired
  private Physician physician;

  @Override
  public void processPatient(Patient patient) {
    switch (patient.getMethod()) {
      case Healer.TRADITIONAL -> {
        physician.treat(patient);
        break;
      }
      case Healer.FOLK -> {
        cleric.treat(patient);
        break;
      }
      case Healer.ALCOHOL -> {
        alcoDoctor.treat(patient);
        break;
      }
      default -> defaultHealer.treat(patient);
    }
  }
}
