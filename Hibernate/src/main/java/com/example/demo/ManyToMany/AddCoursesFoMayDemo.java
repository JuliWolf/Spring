package com.example.demo.ManyToMany;

import com.example.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AddCoursesFoMayDemo {

  public static void main(String[] args) {
    // create session factory
    SessionFactory factory = new Configuration()
        .configure("hibernate.cfg.xml")
        .addAnnotatedClass(Instructor.class)
        .addAnnotatedClass(InstructorDetail.class)
        .addAnnotatedClass(Course.class)
        .addAnnotatedClass(Review.class)
        .addAnnotatedClass(Student.class)
        .buildSessionFactory();

    // create session
    Session session = factory.getCurrentSession();

    try {
      // start a transaction
      session.beginTransaction();

      // get the student May from db
      int studentId = 6;
      Student tempStudent = session.get(Student.class, studentId);

      System.out.println("Laded student: " + tempStudent);
      System.out.println("Course: " + tempStudent.getCourses());

      // create more courses
      Course course1 = new Course("Rubik's Cube - How to Speed Cube");
      Course course2 = new Course("Atari 2600 - Game Development");

      // add student to courses
      course1.addStudent(tempStudent);
      course2.addStudent(tempStudent);

      // save the courses
      System.out.println("Saving the courses...");
      session.save(course1);
      session.save(course2);

      // commit transaction
      session.getTransaction().commit();

      System.out.println("Done!");
    }finally {
      // add clean up code
      session.close();

      factory.close();
    }
  }
}
