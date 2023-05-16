package com.example.unsafe_starter;

import com.example.unsafe_starter.dataExtractor.DataExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author JuliWolf
 * @date 14.05.2023
 */
@Component
public class DataExtractorResolver {
  @Autowired
  private Map<String, DataExtractor> extractorMap;

  public DataExtractor resolve (String pathToData) {
    String fileExtension = pathToData.split("\\.")[1];
    return extractorMap.get(fileExtension);
  }
}
