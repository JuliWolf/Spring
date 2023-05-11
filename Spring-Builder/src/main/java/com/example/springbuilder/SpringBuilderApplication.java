package com.example.springbuilder;

import com.example.springbuilder.factory.ObjectFactory;
import com.example.springbuilder.models.Room;

public class SpringBuilderApplication {

  public static void main(String[] args) {
    CoronaDesinfector coronaDesinfector = ObjectFactory.getInstance().createObject(CoronaDesinfector.class);
    coronaDesinfector.start(new Room());
  }

}
