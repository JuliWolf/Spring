package com.example.starter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 * @author JuliWolf
 * @date 13.05.2023
 */
public interface Finalizer {
  Object doAction (Dataset<Row> rowDataset);
}
