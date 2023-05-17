package com.example.unsafe_starter.filterTransformation;

import com.example.unsafe_starter.OrderedBag;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author JuliWolf
 * @date 16.05.2023
 */
@Component("greaterThan")
public class GreaterThanFilter implements FilterTransformation {
  @Override
  public Dataset<Row> transform(Dataset<Row> dataset, List<String> columnNames, OrderedBag<Object> args) {
    return dataset.filter(functions.col(columnNames.get(0)).geq(args.takeAndRemove()));
  }
}
