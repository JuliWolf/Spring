package com.example.springpatterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringPatternsApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringPatternsApplication.class, args);
  }

}
