package com.injectList.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
@Configuration
public class InjectListConfiguration {
  @Bean
  public InjectListBPP injectListBPP () {
    return new InjectListBPP();
  }
}
