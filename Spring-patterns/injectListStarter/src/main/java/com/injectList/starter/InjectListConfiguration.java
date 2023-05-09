package com.injectList.starter;

import org.springframework.aop.support.DefaultPointcutAdvisor;
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

  @Bean
  public CustomPointcut customPointcut () {
    return new CustomPointcut();
  }

  @Bean
  public ExceptionHandlerAspect exceptionHandlerAspect () {
    return new ExceptionHandlerAspect();
  }

  @Bean
  public DefaultPointcutAdvisor defaultPointcutAdvisor () {
    // Связываем customPointcut с фильтром классов, в котором будет происходить обработка
    return new DefaultPointcutAdvisor(customPointcut(), exceptionHandlerAspect());
  }
}
