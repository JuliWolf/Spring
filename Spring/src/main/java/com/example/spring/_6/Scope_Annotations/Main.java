package com.example.spring._6.Scope_Annotations;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        // read the spring configuration file
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("six_annotations.xml");

        // get the bean from spring container
        Coach theCoach = context.getBean("tennisCoach", Coach.class);
        Coach alphaCoach = context.getBean("tennisCoach", Coach.class);

        // check if they are the same
        boolean result = (theCoach == alphaCoach);

        // print out the result
        System.out.println("\n Pointing to the same object: " + result);

        System.out.println("\n Memory location for theCoach: " + theCoach);
        System.out.println("\n Memory location for alphaCoach: " + alphaCoach + "\n");

        // close the context
        context.close();
    }
}
