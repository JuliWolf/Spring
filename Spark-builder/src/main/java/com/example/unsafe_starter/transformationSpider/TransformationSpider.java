package com.example.unsafe_starter.transformationSpider;

import com.example.unsafe_starter.filterTransformation.SparkTransformation;

import java.util.List;
import java.util.Set;

/**
 * @author JuliWolf
 * @date 14.05.2023
 */
public interface TransformationSpider {
  SparkTransformation getTransformation(List<String> methodWords, Set<String> fieldNamed);
}
