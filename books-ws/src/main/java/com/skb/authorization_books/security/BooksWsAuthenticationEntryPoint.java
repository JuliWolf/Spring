package com.skb.authorization_books.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class BooksWsAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//    response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
    PrintWriter writer = response.getWriter();
    writer.println("Basic Authentication required. Please supply appropriate credentials");
  }

  @Override
  public void afterPropertiesSet() {
    setRealmName("basicRealm");
    super.afterPropertiesSet();
  }
}
