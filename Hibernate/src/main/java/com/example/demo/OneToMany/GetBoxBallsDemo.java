package com.example.demo.OneToMany;

import com.example.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class GetBoxBallsDemo {

  public static void main(String[] args) {
    // create session factory
    SessionFactory factory = new Configuration()
        .configure("hibernate.cfg.xml")
        .addAnnotatedClass(Ball.class)
        .addAnnotatedClass(Box.class)
        .buildSessionFactory();

    // create session
    Session session = factory.openSession();

    try {
      // start a transaction
      session.beginTransaction();

      // get the box
      int theId = 1;
      Box tempBox = session.get(Box.class, theId);

      // print the box
      System.out.println(tempBox);

      List<Ball> balls = tempBox.getBalls();

      for (Ball ball : balls) {
        System.out.println("ball: " + ball);
      }

//      Query<Box> query = session.createQuery(
//          "select i from Box i JOIN FETCH i.balls where i.id=:theId",
//          Box.class
//      );
//
//      // set parameter on query
//      query.setParameter("theId", theId);
//
//      Box singleResult = query.getSingleResult();
//
//      System.out.println("singleResult: " + singleResult);

      // commit transaction
      session.getTransaction().commit();
    } finally {
      // add clean up code
      session.close();

      factory.close();
    }
  }

}
