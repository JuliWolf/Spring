package com.example.spring._1.Inversion_of_Control;

public class MyApp {
    public static void main(String[] args) {
        // create the object
        Coach theCoach = new BaseballCoach();
        Coach trackCoach = new TrackCoach();

        // use the object
        System.out.println(theCoach.getDailyWorkout());
        System.out.println(trackCoach.getDailyWorkout());
    }
}
