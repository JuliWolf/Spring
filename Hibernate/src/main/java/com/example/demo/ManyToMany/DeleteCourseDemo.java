package com.example.demo.ManyToMany;

import com.example.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DeleteCourseDemo {

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

      // get the course from db
      int courseId = 4;
      Course tempCourse = session.get(Course.class, courseId);

      System.out.println("Laded course: " + tempCourse);

      // delete the course
      System.out.println("Deleting course: " + tempCourse);
      session.delete(tempCourse);

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
