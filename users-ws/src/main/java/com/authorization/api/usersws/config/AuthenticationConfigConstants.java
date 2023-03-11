package com.authorization.api.usersws.config;

public class AuthenticationConfigConstants {
  public static final long EXPIRATION_TIME = 5 * 60 * 60 * 1000; // 10 days
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
}
