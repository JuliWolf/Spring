package com.example.unsafe_starter.transformationSpider;

import com.example.unsafe_starter.filterTransformation.SortTransformation;
import com.example.unsafe_starter.filterTransformation.SparkTransformation;
import com.example.unsafe_starter.utils.WordsMatcher;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.List;
import java.util.Set;

/**
 * @author JuliWolf
 * @date 14.05.2023
 */
@Component("orderBy")
public class OrderByTransformationSpider implements TransformationSpider {
  @Override
  public Tuple2<SparkTransformation, List<String>> getTransformation(List<String> methodWords, Set<String> fieldNamed) {
//    WordsMatcher.findAndRemoveMatchingPiecesIfExists()
//    return new Tuple2<>(new SortTransformation(), );

    return null;
  }
}
