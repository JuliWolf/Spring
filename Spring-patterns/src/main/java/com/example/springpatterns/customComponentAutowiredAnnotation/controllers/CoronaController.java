package com.example.springpatterns.customComponentAutowiredAnnotation.controllers;

import com.example.springpatterns.customComponentAutowiredAnnotation.Hospital;
import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;
import com.example.springpatterns.dynamic_aop.psr.PSRService;
import com.example.springpatterns.dynamic_aop.psr.PatientStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;

/**
 * @author JuliWolf
 * @date 08.05.2023
 */
@RestController
@RequestMapping("/corona/")
@RequiredArgsConstructor
public class CoronaController {
  private final Hospital hospital;

  private final PSRService diagnosticService;

  @PostMapping("treat")
  public void treat(@RequestBody Patient patient) {
    hospital.processPatient(patient);
  }

  @GetMapping("diagnose")
  public PatientStatus getPatientStatus () {
    return new PatientStatus(diagnosticService.isPositive(), now());
  }
}
