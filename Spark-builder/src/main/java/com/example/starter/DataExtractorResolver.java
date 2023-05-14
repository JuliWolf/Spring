package com.example.starter;

import java.util.Map;

/**
 * @author JuliWolf
 * @date 14.05.2023
 */
public class DataExtractorResolver {
  private Map<String, DataExtractor> extractorMap;

  public DataExtractor resolve (String pathToData) {
    String fileExtension = pathToData.split("\\.")[1];
    return extractorMap.get(fileExtension);
  }
}
