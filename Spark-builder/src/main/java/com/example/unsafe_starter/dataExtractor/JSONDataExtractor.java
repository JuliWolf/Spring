package com.example.unsafe_starter.dataExtractor;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author JuliWolf
 * @date 13.05.2023
 */
@Component("json")
public class JSONDataExtractor implements DataExtractor {
  @Override
  public Dataset<Row> load(String pathToData, ConfigurableApplicationContext context) {
    return context.getBean(SparkSession.class).read().json(pathToData);
  }
}
