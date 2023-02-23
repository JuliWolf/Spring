package com.example.spring._9.AOP_Pointcut_Declarations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.example.spring._9.AOP_Pointcut_Declarations")
public class DemoConfig {
}
