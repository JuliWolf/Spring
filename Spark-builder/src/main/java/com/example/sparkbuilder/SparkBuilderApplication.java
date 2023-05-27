package com.example.sparkbuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication()
public class SparkBuilderApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(SparkBuilderApplication.class, args);
//    SpeakerRepo speakerRepo = context.getBean(SpeakerRepo.class);
//    List<Speaker> speakers = speakerRepo.findByAgeBetween(20, 35);
//    speakers.forEach(System.out::println);

    CriminalRepo criminalRepo = context.getBean(CriminalRepo.class);
//    List<Criminal> criminals = criminalRepo.findByNumberGreaterThanOrderByNumber(15);
//    criminals.forEach(System.out::println);
    List<Criminal> criminals = criminalRepo.findByNameContains("ova");
    if (criminals.get(0) != null) {
      criminals.get(0).getOrders().forEach(System.out::println);
    }

  }

}
