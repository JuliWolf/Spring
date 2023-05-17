package com.example.unsafe_starter.transformationSpider;

import com.example.unsafe_starter.filterTransformation.SortTransformation;
import com.example.unsafe_starter.filterTransformation.SparkTransformation;
import com.example.unsafe_starter.utils.WordsMatcher;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.ArrayList;
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
    // Сюда придет как минимум одно слово для сортировки
    String sortColumn = WordsMatcher.findAndRemoveMatchingPiecesIfExists(fieldNamed, methodWords);

    List<String> additional = new ArrayList<>();
    while (!methodWords.isEmpty() && methodWords.get(0).equalsIgnoreCase("and")) {
      // Убираем `and`
      methodWords.remove(0);
      // кладем слово, следующее за `and`
      additional.add(WordsMatcher.findAndRemoveMatchingPiecesIfExists(fieldNamed, methodWords));
    }

    additional.add(0 , sortColumn);

    return new Tuple2<>(new SortTransformation(), additional);
  }
}
