package com.example.springbuilder;

import com.example.springbuilder.factory.ObjectFactory;
import com.example.springbuilder.models.Policeman;
import com.example.springbuilder.models.PolicemanImpl;
import com.example.springbuilder.models.Room;

import java.util.HashMap;
import java.util.Map;

public class SpringBuilderApplication {

  public static void main(String[] args) {
//    CoronaDesinfector coronaDesinfector = ObjectFactory.getInstance().createObject(CoronaDesinfector.class);
    ApplicationContext context = Application.run("com.example.springbuilder", new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class)));
    CoronaDesinfector coronaDesinfector = context.getObject(CoronaDesinfector.class);
    coronaDesinfector.start(new Room());
  }

}
