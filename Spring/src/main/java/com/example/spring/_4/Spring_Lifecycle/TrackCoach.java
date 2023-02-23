package com.example.spring._4.Spring_Lifecycle;

import com.example.spring._2.Dependency_injections.Coach;
import com.example.spring._2.Dependency_injections.FortuneService;

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

    // add an init method
    public void doMyStartupStuff () {
        System.out.println("TrackCoach: inside method - doMyStartupStuff");
    }

    // add a destroy method
    public void doMyCleanupStuffYoYo () {
        System.out.println("TrackCoach: inside method - doMyCleanupStuffYoYo");
    }
}
