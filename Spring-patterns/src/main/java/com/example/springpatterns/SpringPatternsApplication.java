package com.example.springpatterns;

import com.example.springpatterns.customComponentAutowiredAnnotation.Patient;
import com.example.springpatterns.customComponentAutowiredAnnotation.Священник;
import com.example.springpatterns.customComponentAutowiredAnnotation.Целитель;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringPatternsApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(SpringPatternsApplication.class, args);

    context.getBean(Священник.class).исцелять(Patient.builder().build());
  }

}
