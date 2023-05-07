package com.injectList.starter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface InjectList {
  Class[] value();
}
