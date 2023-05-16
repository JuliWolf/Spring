package com.example.unsafe_starter.transformationSpider;

import com.example.unsafe_starter.filterTransformation.SparkTransformation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author JuliWolf
 * @date 14.05.2023
 */
@Component("orderBy")
public class OrderByTransformationSpider implements TransformationSpider {
  @Override
  public SparkTransformation getTransformation(List<String> methodWords, Set<String> fieldNamed) {
    return null;
  }
}
