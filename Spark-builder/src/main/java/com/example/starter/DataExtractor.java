package com.example.starter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author JuliWolf
 * @date 13.05.2023
 */
public interface DataExtractor {
  Dataset<Row> load(String pathToData, ConfigurableApplicationContext context);
}
