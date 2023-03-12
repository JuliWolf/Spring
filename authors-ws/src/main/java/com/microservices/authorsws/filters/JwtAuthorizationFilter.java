package com.microservices.authorsws.filters;

import com.microservices.authorsws.config.AuthenticationConfigConstants;
import com.microservices.authorsws.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
// класс OncePerRequestFilter гарантирует, что фильтр будет использоваться единожды для каждого запроса

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {
    final String requestTokenHeader = request.getHeader(AuthenticationConfigConstants.HEADER_STRING);

    // Проверяем строку их заголовка `Authorization`
    if (!hasAuthorizationBearer(requestTokenHeader)) {
      filterChain.doFilter(request, response);

      return;
    }

    // Обрезаем строку, оставляем только токен
    String jwtToken = getAccessToken(requestTokenHeader);

    try {
      // Парсим токен и получаем имя пользователя
      String username = jwtTokenUtil.getUsernameFromToken(jwtToken);

      if (!hasAuthorizationUser(username)) {
        filterChain.doFilter(request, response);

        return;
      }

      // Проверяем валидность токена
      if (!jwtTokenUtil.validateToken(jwtToken)) {
        filterChain.doFilter(request, response);

        return;
      }

      Set<SimpleGrantedAuthority> authorities = getAuthorities(jwtToken);

      // Создаем экземпляр класса для дальнейше передачи его в AuthenticationManager
      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          username,
          null,
          authorities
      );
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder
          .getContext()
          .setAuthentication(authToken);

      filterChain.doFilter(request, response);
    } catch (IllegalArgumentException e) {
      logger.error("Unable to fetch JWT Token");
    } catch (ExpiredJwtException e) {
      logger.error("JWT Token is expired");
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  private boolean hasAuthorizationBearer(String header) {
    if (ObjectUtils.isEmpty(header) || !header.startsWith(AuthenticationConfigConstants.TOKEN_PREFIX)) {
      return false;
    }

    return true;
  }

  private Set<SimpleGrantedAuthority> getAuthorities (String jwtToken) {
    String roles = jwtTokenUtil.getCustomClaimFromToken(jwtToken, "roles");

    return Stream
        .of(roles.split(","))
        .map(a -> new SimpleGrantedAuthority(a))
        .collect(Collectors.toSet());
  }

  // Проверяем что полученный пользователь не пустой
  // Вспомогательный класс SecurityContextHolder хранит в себе данные о текущем аутентифицированном пользователе
  private boolean hasAuthorizationUser (String username) {
    return (
        StringUtils.isNoneEmpty(username) &&
        null == SecurityContextHolder.getContext().getAuthentication()
    );
  }

  private String getAccessToken(String header) {
    String token = header.split(" ")[1].trim();
    return token;
  }
}