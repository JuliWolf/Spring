package com.example.sparkbuilder;

import com.example.unsafe_starter.annotations.Source;
import com.example.unsafe_starter.annotations.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JuliWolf
 * @date 13.05.2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Source("data/speakers.json")
public class Speaker {
  private String name;
  private int age;

  @Transient
  private String bbb;
}
