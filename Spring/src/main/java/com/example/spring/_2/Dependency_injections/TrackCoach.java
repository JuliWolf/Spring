package com.example.spring._2.Dependency_injections;

public class TrackCoach implements Coach {

    private final FortuneService fortuneService;

    public TrackCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "Run a hard 5k";
    }

    @Override
    public String getDailyFortune() {
        return "Just Fo It: " + fortuneService.getFortune();
    }

    private void doMyStartupStuff() {
    }
}
