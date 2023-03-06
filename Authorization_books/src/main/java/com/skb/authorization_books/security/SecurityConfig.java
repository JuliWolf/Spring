package com.skb.authorization_books.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, BooksWsAuthenticationEntryPoint authenticationEntryPoint, JwtRequestFilter jwtRequestFilter) throws Exception {
    // Enable CORS and disable CSRF
    http = http.cors().and().csrf().disable();

    // Set jwt token authentication
    http = http
        .addFilter(jwtRequestFilter);

    // Set session management to stateless
    http = http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and();

    // Set unauthorized requests exception handler
//    http = http
//        .exceptionHandling()
//        .authenticationEntryPoint(
//            (request, response, ex) -> {
//              response.sendError(
//                  HttpServletResponse.SC_UNAUTHORIZED,
//                  ex.getMessage()
//              );
//            }
//        )
//        .and();

    // Set authentication entry point
//    http = http.httpBasic().authenticationEntryPoint(authenticationEntryPoint).and();


    // Set permissions on endpoints
    http.authorizeHttpRequests()
        // Our public endpoints
        .requestMatchers("/api/public/**").permitAll()
        // User endpoints
//        .requestMatchers("/v1/books/{bookId}").hasAnyAuthority("USER", "ADMIN")
        .requestMatchers("/v1/books/{bookId}").access(new WebExpressionAuthorizationManager("hasRole('USER') and hasAuthority('GET_BOOK')"))
        // Admin endpoints
//        .requestMatchers("/v1/books").hasAuthority("ADMIN")
        .requestMatchers("/v1/books").access(new WebExpressionAuthorizationManager("hasRole('ADMIN') and hasAuthority('CREATE_BOOK')"));
        // Our private endpoints
//        .anyRequest().authenticated();

    // Add JWT token filter
//    http.addFilterBefore(
//        jwtTokenFilter,
//        UsernamePasswordAuthenticationFilter.class
//    );

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
    return http
        .getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailsService)
        .passwordEncoder(bCryptPasswordEncoder)
        .and()
//        .inMemoryAuthentication().withUser("").authorities().roles()
//        .and()
        .build();
  }

  // Used by Spring Security if CORS is enabled.
  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source =
        new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

}
