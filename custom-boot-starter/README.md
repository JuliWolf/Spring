1. Создать класс конфигурации
    ```
    @Configuration
    @ConditionalOnClass({SocialConfigurerAdapter.class, VKontakteConnectionFactory.class})
    @ConditionalOnProperty(prefix= "ru.shadam.social-vkontakte", name = { "client-id", "client-secret"})
    @AutoConfigureBefore(SocialWebAutoConfiguration.class)
    @AutoConfigureAfter(WebMvcAutoConfiguration.class)
      public class VKontakteAutoConfiguration {
    }
    ```

    `@Configuration` - означает что данный класс будет обрабатываться Spring Boot</br>
    `@ConditionalOnClass` - означает, чтобы бины будут создаваться при наличии в classpath `SocialConfigurerAdapter` и `VKontakteConnectionFactory`</br>
    `@ConditionalOnProperty` - означает, что бины будут создаваться только при наличии property `ru.shadam.social-vkontakte.client-id` и `ru.shadam.social-vkontakte.client-secret.`</br>
    `@AutoConfigureBefore`, `@AutoConfigureAfter` - означает, что бин будет иницйиализироваться после WebMvc и до `SocialWeb`

2. Расширяем `SocialConfigurationAdapter`, который нужен для того, чтобы зарегистрировать `ConnectionFactory`</br>
Для этого используем метод `addConnectionFactories(ConnectionFactoryConfigurer, Environment)`

3. Создать свой сервис

4. В файле `spring.factories` в папке `resources/META_INF` указать спрингу где смотреть файл конфигурации\
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.example.customBootStarter.MartianTimeAutoConfiguration
```

5. Теперь можно подключать данную зависимость
```
<dependency>
    <groupId>com.martianpost</groupId>
    <artifactId>martian-time-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```