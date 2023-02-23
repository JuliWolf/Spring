package com.example.demo.ManyToMany;

import com.example.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateCourseAndStudentsDemo {

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

      // create a course
      Course tempCourse = new Course("Pacman - How to score one million points");

      // save the course
      System.out.println("saving the course");
      session.save(tempCourse);
      System.out.println("Save the course: " + tempCourse);

      // create the students
      Student student1 = new Student("John", "Doe", "john@test.com");
      Student student2 = new Student("May", "Public", "may@test.com");

      // add students to the course
      tempCourse.addStudent(student1);
      tempCourse.addStudent(student2);

      // save the students
      System.out.println("Saving students...");
      session.save(student1);
      session.save(student2);
      System.out.println("Saved students: " + tempCourse.getStudents());

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
