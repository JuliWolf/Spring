package com.example.sparkbuilder;

import com.example.unsafe_starter.annotations.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * @author JuliWolf
 * @date 27.05.2023
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Source("Spark-builder/data/orders.csv")
public class Order {
  private String name;
  private String desc;
  private long price;
  private long criminalId;
}
