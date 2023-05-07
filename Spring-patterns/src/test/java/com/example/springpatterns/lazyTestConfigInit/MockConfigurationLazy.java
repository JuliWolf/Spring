package com.example.springpatterns.lazyTestConfigInit;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author JuliWolf
 * @date 07.05.2023
 */
@Configuration
@ComponentScan(lazyInit = true)
public class MockConfigurationLazy {
}
