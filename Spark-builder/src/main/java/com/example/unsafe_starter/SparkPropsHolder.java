package com.example.unsafe_starter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author JuliWolf
 * @date 14.05.2023
 */

@ConfigurationProperties(prefix = "spark")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SparkPropsHolder {
  private String appName;
  private String packagesToScan;
}
