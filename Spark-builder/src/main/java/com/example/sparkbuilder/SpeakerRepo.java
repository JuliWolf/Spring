package com.example.sparkbuilder;

import com.example.unsafe_starter.SparkRepository;

import java.util.List;

/**
 * @author JuliWolf
 * @date 13.05.2023
 */
public interface SpeakerRepo extends SparkRepository<Speaker> {

  List<Speaker> findByAgeBetween (int min, int max);

  List<Speaker> findByAgeGreaterThan(int min);

  long findByAgeGreaterThanCount(int min);
}
