package com.example.spring._5.Annotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TennisCoach implements Coach {

    // Field injection (not recommended https://habr.com/ru/post/334636/)
    @Autowired
    @Qualifier("randomFortuneService")
    private FortuneService fortuneService;

    // define a default constructor
    public TennisCoach () {
        System.out.println(">> TennisCoach: inside default constructor");
    }

    // Constructor injection
//    @Autowired
//    public TennisCoach (FortuneService fortuneService) {
//        this.fortuneService = fortuneService;
//    }

    // Setter injection
//    @Autowired
//    public void setFortuneService (FortuneService fortuneService) {
//        System.out.println(">> TennisCoach: inside method setFortuneService");
//        this.fortuneService = fortuneService;
//    }

    // Method injection
//    @Autowired
//    public void doSomeCrazyStuff (FortuneService fortuneService) {
//        System.out.println(">> TennisCoach: inside method doSomeCrazyStuff");
//        this.fortuneService = fortuneService;
//    }

    @Override
    public String getDailyWorkout() {
        return "Practice your backhand volley";
    }

    @Override
    public String getDailyFortune() {
        return fortuneService.getFortune();
    }
}
