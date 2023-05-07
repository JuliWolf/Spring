package com.example.springpatterns.customComponentAutowiredAnnotation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
  private String name;
  private int age;
  private String method;

}
