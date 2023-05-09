package com.example.springpatterns.dynamic_aop.psr;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * @author JuliWolf
 * @date 09.05.2023
 */
@Value
@AllArgsConstructor
public class PatientStatus {
  Boolean positive;
  LocalDateTime when;
}
