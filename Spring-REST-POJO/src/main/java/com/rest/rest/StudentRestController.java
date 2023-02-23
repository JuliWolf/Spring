package com.rest.rest;

import com.rest.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

  private List<Student> students;

  // define @PostConstruct to load the student fata ... only once
  @PostConstruct
  public void loadData () {
    students = new ArrayList<>();

    students.add(new Student("Mary", "Pate"));
    students.add(new Student("John", "Black"));
    students.add(new Student("Nick", "Mitchel"));
  }

  // define endpoint for "/students" - return list of students
  @GetMapping("/students")
  public List<Student> getStudents () {
    return students;
  }

  // define endpoint for "/students/{studentId}" - return student at index
  @GetMapping("/students/{studentId}")
  public Student getStudent (@PathVariable int studentId) {

    // check the studentId against list size
    if ((studentId >= students.size()) || (studentId < 0) ) {
      throw new StudentNotFoundException("Student id is not found - " + studentId);
    }

    return students.get(studentId);
  }
}
