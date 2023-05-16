package com.example.unsafe_starter.filterTransformation;

import com.example.unsafe_starter.OrderedBag;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.List;

/**
 * @author JuliWolf
 * @date 13.05.2023
 */
public interface SparkTransformation {
  Dataset<Row> transform(Dataset<Row> dataset, List<String> columnNames, OrderedBag<Object> args);
}
