package com.example.sparkbuilder;

import com.example.unsafe_starter.SparkRepository;

import java.util.List;

/**
 * @author JuliWolf
 * @date 17.05.2023
 */
public interface CriminalRepo extends SparkRepository<Criminal> {
  List<Criminal> findByNumberGreaterThanOrderByNumber(int min);

  long findByNameContainsCount(String s);
}
