package com.skb.authorization_books.utils;

import com.skb.authorization_books.config.AuthenticationConfigConstants;
import com.skb.authorization_books.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

  Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  public String getUsernameFromToken(String token) {
    // Парсим токен и забираем оттуда subject в котором будет храниться имя пользователя
    // https://auth0.com/docs/secure/tokens/json-web-tokens/json-web-token-claims
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts
        .builder()
        .setClaims(claims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + AuthenticationConfigConstants.EXPIRATION_TIME))
        .signWith(key)
        .compact();
  }

  public Boolean validateToken(String token, User user) {
    final String username = getUsernameFromToken(token);
    // Проверяем что полученное имя пользователя сходится с именем из токена
    // Проверяем не протух ли токен
    return (
        username
        .equals(user.getUsername()) && !isTokenExpired(token)
    );
  }
}
