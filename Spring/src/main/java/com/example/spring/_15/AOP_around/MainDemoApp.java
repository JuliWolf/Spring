package com.example.spring._15.AOP_around;

import com.example.spring._15.AOP_around.service.TrafficFortuneService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Logger;

public class MainDemoApp {

  private static Logger logger = Logger.getLogger(MainDemoApp.class.getName());

  public static void main(String[] args) {
    // read spring config java class
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig.class);

    // get the bean from spring container
    TrafficFortuneService fortuneService = context.getBean("trafficFortuneService", TrafficFortuneService.class);

    logger.info("\nMain Program: AroundDemoApp");

    logger.info("Calling getFortune");

    boolean tripWire = true;
    String fortune = fortuneService.getFortune(tripWire);

    logger.info("\nMy fortune is: " + fortune);

    logger.info("\nFinished");
    // close the context
    context.close();
  }
}
