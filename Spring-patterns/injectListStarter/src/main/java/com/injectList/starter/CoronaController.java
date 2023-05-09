package com.injectList.starter;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author JuliWolf
 * @date 09.05.2023
 */
@RestController
@Retention(RetentionPolicy.RUNTIME)
public @interface CoronaController {
}
