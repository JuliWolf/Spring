package com.example.spring._13.AOP_after_throwing;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.example.spring._13.AOP_after_throwing")
public class DemoConfig {
}
