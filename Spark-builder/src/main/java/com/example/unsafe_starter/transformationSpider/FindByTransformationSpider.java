package com.example.unsafe_starter.transformationSpider;

import com.example.unsafe_starter.filterTransformation.FilterTransformation;
import com.example.unsafe_starter.filterTransformation.SparkTransformation;
import com.example.unsafe_starter.utils.WordsMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
  public SparkTransformation getTransformation(List<String> methodWords, Set<String> fieldNamed) {
    List<String> fieldName = List.of(WordsMatcher.findAndRemoveMatchingPiecesIfExists(fieldNamed, methodWords));
    String filterName = WordsMatcher.findAndRemoveMatchingPiecesIfExists(filterTransformationMap.keySet(), methodWords);
    return filterTransformationMap.get(filterName);
  }
}
