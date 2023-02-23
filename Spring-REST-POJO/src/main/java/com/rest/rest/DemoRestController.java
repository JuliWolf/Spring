package com.rest.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class DemoRestController {

  // add code for the /hello endpoint
  @GetMapping("/hello")
  public String sayHello () {
    return "Hello World!";
  }

  // inject properties for: coach.name and team.name
  @Value("${coach.name}")
  private String coachName;

  @Value("${team.name}")
  private String teamName;

  // expose new endpoint for "teaminfo"
  @GetMapping("/teaminfo")
  public String getTeamInfo () {
    return "Coach: " + coachName + ", Team name: " + teamName;
  }

}
