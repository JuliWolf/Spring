package com.example.mockito.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JuliWolf
 * @date 03.06.2023
 */
@RestController
public class HelloWorldController {
  @GetMapping("/hello")
  public String helloWorld () {
    return "hello world";
  }
}
