package com.example.demo.OneToMany;

import com.example.entity.Course;
import com.example.entity.Instructor;
import com.example.entity.InstructorDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateCoursesDemo {

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

      // create some courses
      Course guitarCourse = new Course("Air Guitar - The Ultimate Guide");
      Course pinballCourse = new Course("The Pinball Masterclass");

      // add courses to instructor
      tempInstructor.add(guitarCourse);
      tempInstructor.add(pinballCourse);

      // save courses
      session.save(guitarCourse);
      session.save(pinballCourse);

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
