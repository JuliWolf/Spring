package com.example.springbuilder.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author JuliWolf
 * @date 10.05.2023
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectProperty {
  String value() default "";
}
