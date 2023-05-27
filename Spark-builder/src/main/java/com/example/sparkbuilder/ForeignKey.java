package com.example.sparkbuilder;/**
 * @author JuliWolf
 * @date 27.05.2023
 */

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface ForeignKey {
  String value();
}
