package com.example.demo.EagerVsLazy;

import com.example.entity.Course;
import com.example.entity.Instructor;
import com.example.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class FetchJoinDemo {

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

      // option 2: Hibernate query with HQL

      // get the instructor from db
      int theId = 5;

      Query<Instructor> query = session.createQuery(
          "select i from Instructor i JOIN FETCH i.courses where i.id=:theId",
          Instructor.class
      );

      // set parameter on query
      query.setParameter("theId", theId);

      // execute query and get instructor
      Instructor tempInstructor = query.getSingleResult();

      System.out.println("Instructor: " + tempInstructor);

      // commit transaction
      session.getTransaction().commit();

      // close the session
      session.close();

      System.out.println("The session is now closed!");

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
