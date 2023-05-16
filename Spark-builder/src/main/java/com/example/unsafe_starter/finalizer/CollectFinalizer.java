package com.example.unsafe_starter.finalizer;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

/**
 * @author JuliWolf
 * @date 14.05.2023
 */
@Component("collect")
public class CollectFinalizer implements Finalizer {
  @Override
  public Object doAction(Dataset<Row> dataset, Class<?> model) {
    Encoder<?> encoder = Encoders.bean(model);
    return dataset.as(encoder).collectAsList();
  }
}
