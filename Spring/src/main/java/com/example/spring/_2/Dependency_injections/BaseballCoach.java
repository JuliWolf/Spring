package com.example.spring._2.Dependency_injections;

public class BaseballCoach implements Coach {
    // Define a private field for the dependency
    private final FortuneService fortuneService;

    // define a constructor for dependency injection
    public BaseballCoach (FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "Spend 30 minutes on batting practice";
    }

    @Override
    public String getDailyFortune() {
        // use my fortune to get a fortune
        return fortuneService.getFortune();
    }
}
