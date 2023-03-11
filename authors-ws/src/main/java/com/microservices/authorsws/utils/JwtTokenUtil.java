package com.microservices.authorsws.utils;

import com.microservices.authorsws.config.AuthenticationConfigConstants;
import com.microservices.authorsws.userDetails.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

  private static String secret = System.getenv("jwtSecret");

  byte[] secretKeyBytes = secret.getBytes();

  public String getUsernameFromToken(String token) {
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
        .setSigningKey(secretKeyBytes)
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
        .signWith(SignatureAlgorithm.HS512, secretKeyBytes)
        .compact();
  }

  public Boolean validateToken(String token, User user) {
    final String username = getUsernameFromToken(token);
    return (
        username
        .equals(user.getUsername()) && !isTokenExpired(token)
    );
  }
}
