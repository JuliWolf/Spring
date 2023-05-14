package com.example.starter;

import java.lang.reflect.Method;

/**
 * @author JuliWolf
 * @date 14.05.2023
 */
public interface TransformationSpider {
  SparkTransformation getTransformation(Method[] methods);
}
