package com.authorization.api.usersws.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {
  @GetMapping("login")
  public String loginUser (@AuthenticationPrincipal OAuth2User oAuth2User) {
    if (oAuth2User == null) return null;

    return "Username: " + oAuth2User.getName() + "<br>" +
        "User Authorities: " + oAuth2User.getAuthorities();
  }
}
