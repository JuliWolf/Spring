package com.example.mockito.controller;

import com.example.mockito.entity.Student;
import com.example.mockito.service.StudentBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JuliWolf
 * @date 03.06.2023
 */
@RestController
public class HelloWorldController {

  @Autowired
  private StudentBusinessService studentBusinessService;

  @GetMapping("/hello")
  public String helloWorld () {
    return "hello world";
  }

  @GetMapping("/sample-student")
  public Student getStudentDetails () {
    return new Student(100, "Peter", "England");
  }

  @GetMapping("/student-business")
  public Student getStudentBusinessDetails () {
    return studentBusinessService.getStudentDetails();
  }

  @GetMapping("/all-students")
  public List<Student> getAllStudentsDetails () {
    return studentBusinessService.getAllStudents();
  }
}
