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

