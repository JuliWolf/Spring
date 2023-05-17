package com.example.unsafe_starter.transformationSpider;

import com.example.unsafe_starter.filterTransformation.FilterTransformation;
import com.example.unsafe_starter.filterTransformation.SparkTransformation;
import com.example.unsafe_starter.utils.WordsMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author JuliWolf
 * @date 14.05.2023
 */
@Component("findBy")
@RequiredArgsConstructor
public class FindByTransformationSpider implements TransformationSpider {

  private final Map<String, FilterTransformation> filterTransformationMap;

  @Override
  public Tuple2<SparkTransformation, List<String>> getTransformation(List<String> methodWords, Set<String> fieldNames) {
    List<String> columnNames = List.of(WordsMatcher.findAndRemoveMatchingPiecesIfExists(fieldNames, methodWords));
    String filterName = WordsMatcher.findAndRemoveMatchingPiecesIfExists(filterTransformationMap.keySet(), methodWords);
    return new Tuple2<>(filterTransformationMap.get(filterName), columnNames);
  }
}
