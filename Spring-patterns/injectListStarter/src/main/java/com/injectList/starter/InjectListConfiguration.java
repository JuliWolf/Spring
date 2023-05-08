package com.injectList.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
@Configuration
@Import(LegacyBeanDefinitionRegistrar.class)
public class InjectListConfiguration {
  @Bean
  public InjectListBPP injectListBPP () {
    return new InjectListBPP();
  }
}
