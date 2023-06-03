package com.example.mockito.controller;

import com.example.mockito.entity.Student;
import com.example.mockito.service.StudentBusinessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author JuliWolf
 * @date 03.06.2023
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(HelloWorldController.class)
public class HelloWorldTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentBusinessService studentBusinessService;

  @Test
  public void helloWorld () {
    // Подготавливаем запрос
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON);
    try {
      // Делаем вызов и получаем результат
      MvcResult mvcResult = mockMvc.perform(requestBuilder)
          // Добавляем ожидаемые события
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().string("hello world"))
          .andReturn();
      // сравниваем результат
      assertEquals("hello world", mvcResult.getResponse().getContentAsString());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void getStudentTest () {
    // Подготавливаем запрос
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/sample-student").accept(MediaType.APPLICATION_JSON);
    try {
      // Делаем вызов и получаем результат
      MvcResult mvcResult = mockMvc.perform(requestBuilder)
          // Добавляем ожидаемые события
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().string("{\"id\":100,\"stdName\":\"Peter\",\"atdAddress\":\"England\"}"))
          .andReturn();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void getStudentBusinessTest () {

    when(studentBusinessService.getStudentDetails()).thenReturn(new Student(100, "Michel", "France"));

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/student-business").accept(MediaType.APPLICATION_JSON);
    try {
      MvcResult mvcResult = mockMvc.perform(requestBuilder)
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().string("{\"id\":100,\"stdName\":\"Michel\",\"atdAddress\":\"France\"}"))
          .andReturn();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
