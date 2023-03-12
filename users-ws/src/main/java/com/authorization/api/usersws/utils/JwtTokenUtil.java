package com.authorization.api.usersws.utils;

import com.authorization.api.usersws.config.AuthenticationConfigConstants;
import com.authorization.api.usersws.userDetails.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil implements Serializable {

  private static String secret = System.getenv("jwtSecret");

  byte[] secretKeyBytes = Base64.getEncoder().encode(secret.getBytes());
  SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());


  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();

    claims.put("roles", user.getAuthorities()
        .stream()
        .map(a -> a.getAuthority())
        .collect(Collectors.joining(","))
    );

    return Jwts
        .builder()
        .setClaims(claims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + AuthenticationConfigConstants.EXPIRATION_TIME))
        .signWith(secretKey, SignatureAlgorithm.HS512)
        .compact();
  }
}
