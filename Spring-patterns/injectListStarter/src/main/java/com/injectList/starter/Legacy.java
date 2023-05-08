package com.injectList.starter;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author JuliWolf
 * @date 08.05.2023
 */
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Legacy {
}
