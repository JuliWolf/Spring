package com.example.starter;

import com.example.unsafe_starter.LazySparkList;
import com.example.unsafe_starter.FirstLevelCacheService;
import com.example.unsafe_starter.aspect.LazyCollectionAspectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author JuliWolf
 * @date 27.05.2023
 */
@Configuration
public class StartConf {
  @Bean
  @Scope("prototype")
  public LazySparkList lazySparkList () {
    return new LazySparkList();
  }

  @Bean
  public FirstLevelCacheService firstLevelCacheService () {
    return new FirstLevelCacheService();
  }

  @Bean
  public LazyCollectionAspectHandler lazyCollectionAspectHandler () {
    return new LazyCollectionAspectHandler();
  }
}
