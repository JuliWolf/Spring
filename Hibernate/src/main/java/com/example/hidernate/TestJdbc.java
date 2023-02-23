package com.example.hidernate;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "123321";

        try {
            System.out.println("Connecting to database:... " + jdbcUrl);
            Connection myConn = DriverManager.getConnection(jdbcUrl, user, password);

            System.out.println("Connection successful!!!");
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}


