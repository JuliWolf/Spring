package com.authorization.api.usersws.utils;

import com.authorization.api.usersws.config.AuthenticationConfigConstants;
import com.authorization.api.usersws.userDetails.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

//  private static String secret = System.getenv("jwtSecret");

  private Key getSigningKey() {
    return Keys.secretKeyFor(SignatureAlgorithm.HS256);
  }

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
        .setSigningKey(getSigningKey())
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
        .signWith(getSigningKey())
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
