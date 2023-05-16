package com.example.unsafe_starter.finalizer;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

/**
 * @author JuliWolf
 * @date 14.05.2023
 */
@Component("count")
public class CountFinalizer implements Finalizer {
  @Override
  public Object doAction(Dataset<Row> dataset, Class<?> model) {
    return dataset.count();
  }
}
