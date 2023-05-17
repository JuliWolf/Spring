package com.example.unsafe_starter.transformationSpider;

import com.example.unsafe_starter.filterTransformation.SparkTransformation;
import scala.Tuple2;

import java.util.List;
import java.util.Set;

/**
 * @author JuliWolf
 * @date 14.05.2023
 */
public interface TransformationSpider {
  Tuple2<SparkTransformation, List<String>> getTransformation(List<String> methodWords, Set<String> fieldNamed);
}
