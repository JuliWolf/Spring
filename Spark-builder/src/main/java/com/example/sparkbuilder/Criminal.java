package com.example.sparkbuilder;

import com.example.unsafe_starter.annotations.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author JuliWolf
 * @date 17.05.2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Source("Spark-builder/data/criminals.csv")
public class Criminal {
  private long id;
  private String name;
  private int number;
}
