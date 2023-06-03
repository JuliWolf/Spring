package com.example.mockito.service;

import com.example.mockito.entity.Student;
import org.springframework.stereotype.Component;

/**
 * @author JuliWolf
 * @date 03.06.2023
 */
@Component
public class StudentBusinessService {
  public Student getStudentDetails () {
    return new Student(100, "Michel", "France");
  }
}
