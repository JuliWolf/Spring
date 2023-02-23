package com.example.spring_hibernate_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan("com.example.spring_hibernate_web.entity")
@EnableTransactionManagement
public class SpringHibernateWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringHibernateWebApplication.class, args);
  }
}
