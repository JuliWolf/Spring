## Настройка сертификата

1. Сформировать сертификат
2. Добавить сертификат в папку resourses
3. В файле `application.properties` прописать настройки
```
server.ssl.key-store=classpath:ssl/serverkeystore.jks
server.ssl.key-store-type=JKS
server.ssl.key-store-password=topsecret
server.ssl.key-alias=tcserver
```

## Настройка классов для авторизации

1. Создать класс `User`, который будет наследоваться от класса `UserDetail`
класс `UserDetail` выступает в качестве адаптека между БД и тем, что трубуется Spring Security внутри `SecurityContextHolder`
2. У класса `User` должны быть свойства `password`  и `username`
3. Создать класс `UserService`, который будет запрашивать данные пользователя по его `username`
4. Создать класс `UserDetailsServiceIml` реализующий интерфейс `UserDetailsService`
Данный класс будет реализовывать единственный метод для получения пользователя `loadUserByUsername`
5. Создать класс `SecurityConfig`, в котором будут описаны правила для запроса авторизации
Реализуем метод `filterChain`
В рамках этого метода будет описана цепочка для проверки запросов
```
http
          .cors()
        .and()
          .csrf()
          .disable()
          .authorizeHttpRequests()
          .anyRequest()
          .authenticated()
        .and()
          .httpBasic()
          .authenticationEntryPoint(authenticationEntryPoint);

    return http.build();
```
В качестве `authenticationEntryPoint` выступает кастомный класс 

Реализуем метод `authenticationManager`
Метод связывает все необходимые классы для проведения проверок авторизации
```
return http
    .getSharedObject(AuthenticationManagerBuilder.class)
    .userDetailsService(userDetailsService)
    .passwordEncoder(bCryptPasswordEncoder)
    .and()
    .build();
```

6. Создать класс `BooksWsAuthenticationEntryPoint`, который будет наследоваться от `BasicAuthenticationEntryPoint`
для изменения значений заголовков
```
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
```


```
SessionCreationPolicy.STATELESS - No session will be created or used.
SessionCreationPolicy.ALWAYS - A session will always be created if it does not exist.
SessionCreationPolicy.NEVER - A session will never be created. But if a session exists, it will be used.
SessionCreationPolicy.IF_REQUIRED - A session will be created if required. (Default Configuration)
```

## Настройка прав

1. Необходимо создать таблицу с правами
```
CREATE TABLE AUTHORITIES (
	AUTHORITY_ID INT PRIMARY KEY,	
    USERNAME VARCHAR(128) NOT NULL,
    AUTHORITY VARCHAR(128) NOT NULL
);
ALTER TABLE AUTHORITIES ADD CONSTRAINT USER_ROLE_UNIQUE UNIQUE (USERNAME, AUTHORITY);
ALTER TABLE AUTHORITIES ADD CONSTRAINT USER_ROLE_FK1 FOREIGN KEY (USERNAME) REFERENCES USERS (USERNAME);
```

2. Реализовать класс `AuthoritiesEntity` для связи с базой данных
```
package com.skb.authorization_books.user;

import jakarta.persistence.*;

@Entity
@Table(name = "authorities")
public class AuthoritiesEntity {
  @Column(name = "authority_id")
  @Id
  private Integer authorityId;

  @Column(name = "authority")
  private String authority;

  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL
  )
  @JoinColumn(name = "username", nullable = false)
  private UserEntity userEntity;

  public UserEntity getUserEntity() {
    return userEntity;
  }

  public void setUserEntity(UserEntity userEntity) {
    this.userEntity = userEntity;
  }

  public Integer getAuthorityId() {
    return authorityId;
  }

  public void setAuthorityId(Integer authorityId) {
    this.authorityId = authorityId;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  public AuthoritiesEntity() {
  }

  public AuthoritiesEntity(Integer authorityId, String authority, UserEntity userEntity) {
    this.authorityId = authorityId;
    this.authority = authority;
    this.userEntity = userEntity;
  }

  @Override
  public String toString() {
    return "AuthoritiesEntity{" +
        "authorityId=" + authorityId +
        ", authority='" + authority + '\'' +
        ", userEntity=" + userEntity +
        '}';
  }
}
```

3. В классе `UserEntity` описать связь с таблицей `autorities`
```
  @OneToMany(
      mappedBy = "userEntity",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL
  )
  private Set<AuthoritiesEntity> authoritiesEntities;

  public Set<AuthoritiesEntity> getAuthoritiesEntities() {
    return authoritiesEntities;
  }

  public void setAuthoritiesEntities(Set<AuthoritiesEntity> authoritiesEntities) {
    this.authoritiesEntities = authoritiesEntities;
  }
```

4. В класс `User` добавить свойства для хранения списка `authorities`
```
  private Set<GrantedAuthority> authorities;

  public void setAuthorities(Set<GrantedAuthority> authorities) {
    this.authorities = authorities;
  }
```

```
@Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }
```

5. Реализовать класс `AuthoritiesRepository` для получения списка прав
```
package com.skb.authorization_books.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends JpaRepository<AuthoritiesEntity, Integer> {
  UserEntity findByUserEntity (UserEntity userEntity);
}
```

6. В файле `SecutiryConfig` добавить проверку прав для эндпоинтов в фильтрах
```
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, BooksWsAuthenticationEntryPoint authenticationEntryPoint) throws Exception {
    // Enable CORS and disable CSRF
    http = http.cors().and().csrf().disable();

    // Set session management to stateless
    http = http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and();

    // Set authentication entry point
    http = http.httpBasic().authenticationEntryPoint(authenticationEntryPoint).and();

    // Set permissions on endpoints
    http.authorizeHttpRequests()
        // Our public endpoints
        .requestMatchers("/api/public/**").permitAll()
        // User endpoints
        .requestMatchers("/v1/books/{bookId}").hasAnyAuthority("USER", "ADMIN")
        // Admin endpoints
        .requestMatchers("/v1/books").hasAuthority("ADMIN")
        // Our private endpoints
        .anyRequest().authenticated();

    return http.build();
  }
```


## Связь прав и ролей

