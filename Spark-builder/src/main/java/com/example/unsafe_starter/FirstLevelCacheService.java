package com.example.unsafe_starter;

import com.example.unsafe_starter.DataExtractorResolver;
import com.example.unsafe_starter.dataExtractor.DataExtractor;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JuliWolf
 * @date 27.05.2023
 */
public class FirstLevelCacheService {
  private Map<Class<?>, Dataset<Row>> model2Dataset = new HashMap<>();

  @Autowired
  private DataExtractorResolver extractorResolver;

  public List readDataFor(long ownerId, Class<?> modelClass, String pathToSource, String foreignKey, ConfigurableApplicationContext context) {
    // Если данных по указанной моделе ранее не было получени
    if (!model2Dataset.containsKey(modelClass)) {
      // Получаем данные
      DataExtractor extractor = extractorResolver.resolve(pathToSource);
      Dataset<Row> dataset = extractor.load(pathToSource, context);
      dataset.persist();
      model2Dataset.put(modelClass, dataset);
    }
    // Определяем encoder по модели
    Encoder<?> encoder = Encoders.bean(modelClass);
    // Фильтруем имеющиеся данные
    return model2Dataset.get(modelClass)
        .filter(functions.col(foreignKey).equalTo(ownerId))
        .as(encoder)
        .collectAsList();
  }
}
