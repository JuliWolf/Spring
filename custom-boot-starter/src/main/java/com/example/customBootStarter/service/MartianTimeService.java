package com.example.customBootStarter.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZonedDateTime;

@Service
public class MartianTimeService {
  private static final ZonedDateTime MIN_DAY = ZonedDateTime.parse("2000-01-06T00:00:00Z");

  public double toMarsSolDate (ZonedDateTime zonedDateTime) {
    double secondsFromMinDay = (double) Duration.between(MIN_DAY, zonedDateTime).getSeconds();

    return secondsFromMinDay / 88775.244 + 44795.9998;
  }
}
