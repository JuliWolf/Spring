package com.example.springpatterns.customComponentAutowiredAnnotation.controllers;

import com.example.springpatterns.customComponentAutowiredAnnotation.Hospital;
import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JuliWolf
 * @date 08.05.2023
 */
@RestController
@RequestMapping("/corona/")
@RequiredArgsConstructor
public class CoronaController {
  private final Hospital hospital;

  @PostMapping("treat")
  public void treat(@RequestBody Patient patient) {
    hospital.processPatient(patient);
  }
}
