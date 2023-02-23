package com.example.demo.OneToMany;

import com.example.entity.Course;
import com.example.entity.Instructor;
import com.example.entity.InstructorDetail;
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
        .buildSessionFactory();

    // create session
    Session session = factory.getCurrentSession();

    try {
      // start a transaction
      session.beginTransaction();

      // get a course
      int courseId = 1;
      Course course = session.get(Course.class, courseId);

      System.out.println("Deleting course: " + course);

      // delete course
      session.delete(course);

      // commit transaction
      session.getTransaction().commit();

      System.out.println("Done!");
    } finally {
      // add clean up code
      session.close();

      factory.close();
    }
  }
}
