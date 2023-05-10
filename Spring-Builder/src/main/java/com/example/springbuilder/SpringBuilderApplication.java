package com.example.springbuilder;

import com.example.springbuilder.models.Room;

public class SpringBuilderApplication {

  public static void main(String[] args) {
    CoronaDesinfector coronaDesinfector = new CoronaDesinfector();
    coronaDesinfector.start(new Room());
  }

}
