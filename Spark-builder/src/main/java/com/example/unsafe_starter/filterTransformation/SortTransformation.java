package com.example.unsafe_starter.filterTransformation;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

/**
 * @author JuliWolf
 * @date 14.05.2023
 */
public class SortTransformation implements SparkTransformation {
  @Override
  public Dataset<Row> transform(Dataset<Row> dataset, List<String> columnNames) {
    return dataset
        .orderBy(
            columnNames.get(0),
            columnNames.stream().skip(1).toArray(String[]::new)
        );
  }
}
