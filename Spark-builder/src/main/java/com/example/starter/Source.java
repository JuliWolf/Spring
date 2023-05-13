package com.example.starter;/**
 * @author JuliWolf
 * @date 13.05.2023
 */

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface Source {
  String value();
}
