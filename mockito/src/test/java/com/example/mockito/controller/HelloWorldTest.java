package com.example.mockito.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

/**
 * @author JuliWolf
 * @date 03.06.2023
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(HelloWorldController.class)
public class HelloWorldTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void helloWorld () {
    // Подготавливаем запрос
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON);
    try {
      // Делаем вызов и получаем результат
      MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
      // сравниваем результат
      assertEquals("hello world", mvcResult.getResponse().getContentAsString());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}