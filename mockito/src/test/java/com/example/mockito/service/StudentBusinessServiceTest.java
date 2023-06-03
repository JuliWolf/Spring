package com.example.mockito.service;

import com.example.mockito.entity.Student;
import com.example.mockito.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author JuliWolf
 * @date 03.06.2023
 */
@ExtendWith(SpringExtension.class)
public class StudentBusinessServiceTest {

  @InjectMocks
  private StudentBusinessService studentBusinessService;

  @Mock
  private StudentRepository studentRepository;

  @Test
  public void getAllStudentsTest () {
    List<Student> students = Arrays.asList(
        new Student(10001, "john", "russia"),
        new Student(10002, "masha", "New York")
    );

    when(studentRepository.findAll())
        .thenReturn(students);

    List<Student> allStudents = studentBusinessService.getAllStudents();

    assertEquals(10001, allStudents.get(0).getId());
    assertEquals(10002, allStudents.get(1).getId());
  }
}
