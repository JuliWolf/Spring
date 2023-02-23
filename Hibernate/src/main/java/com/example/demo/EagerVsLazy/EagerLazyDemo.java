package com.example.demo.EagerVsLazy;

import com.example.entity.Course;
import com.example.entity.Instructor;
import com.example.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class EagerLazyDemo {

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

      // fet the instructor from db
      int theId = 5;
      Instructor tempInstructor = session.get(Instructor.class, theId);

      System.out.println("Instructor: " + tempInstructor);

      System.out.println("Courses: " + tempInstructor.getCourses());

      // commit transaction
      session.getTransaction().commit();

      // close the session
      session.close();

      System.out.println("The session is now closed!");
      // option 1: call getter method while session is open


      // get courses for the instructor
      System.out.println("Courses: " + tempInstructor.getCourses());

      System.out.println("Done!");
    } finally {
      // add clean up code
      session.close();

      factory.close();
    }
  }
}
