package com.example.mockito.service;

import com.example.mockito.entity.Student;
import com.example.mockito.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author JuliWolf
 * @date 03.06.2023
 */
@Component
public class StudentBusinessService {

  @Autowired
  private StudentRepository studentRepository;

  public Student getStudentDetails () {
    return new Student(100, "Michel", "France");
  }

  public List<Student> getAllStudents () {
    return studentRepository.findAll();
  }
}
