package com.example.springbuilder.annotations;/**
 * @author JuliWolf
 * @date 11.05.2023
 */

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface Singleton {
}
