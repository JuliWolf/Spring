package com.example.spring._7.Configuration;

public class SadFortuneService implements FortuneService{
    @Override
    public String getFortune() {
        return "Today is a sad day";
    }
}
