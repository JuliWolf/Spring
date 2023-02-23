package com.example.demo.CRUD;

import com.example.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UpdateStudentDemo {
    public static void main(String[] args) {
        // create session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        // create session
        Session session = factory.getCurrentSession();

        try {
            int studentId = 1;
            //get out student's id: primary key
            System.out.println("Saved student. Generated id: " + studentId);

            // now get a new session and start transaction
            session = factory.getCurrentSession();
            session.beginTransaction();

            Student myStudent = session.get(Student.class, studentId);

            System.out.println("Get complete: " + myStudent);

            System.out.println("Updating student...");
            myStudent.setFirstName("Scooby");

            // commit the transaction
            session.getTransaction().commit();



            // -----
            session = factory.getCurrentSession();
            session.beginTransaction();

            System.out.println("Update email for all students");

            session.createQuery("update Student set email='foo@gmail.com'")
                    .executeUpdate();

            // commit the transaction
            session.getTransaction().commit();

            System.out.println("Done!");
        }finally {
            factory.close();
        }
    }
}
