package com.skb.authorization_books.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skb.authorization_books.config.AuthenticationConfigConstants;
import com.skb.authorization_books.user.User;
import com.skb.authorization_books.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtTokenUtil jwtTokenUtil;
  private final AuthenticationManager authenticationManager;

  @Override
  @Autowired
  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(authenticationManager);
  }

  @Autowired
  public JwtAuthenticationFilter (AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
    this.jwtTokenUtil = jwtTokenUtil;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      User user = new ObjectMapper()
          .readValue(request.getInputStream(), User.class);

      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              user.getUsername(),
              user.getPassword(),
              new ArrayList<>())
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication auth
  ) throws IOException, ServletException {
    User user = (User) auth.getPrincipal();
    String token = jwtTokenUtil.generateToken(user);

    //START - SENDING JWT AS A BODY
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(
        "{\"" + AuthenticationConfigConstants.HEADER_STRING + "\":\"" + AuthenticationConfigConstants.TOKEN_PREFIX + token + "\"}"
    );
    //END - SENDING JWT AS A BODY

    //START - SENDING JWT AS A HEADER
    response.addHeader(AuthenticationConfigConstants.HEADER_STRING, AuthenticationConfigConstants.TOKEN_PREFIX + token);
    //END - SENDING JWT AS A HEADER
  }
}
