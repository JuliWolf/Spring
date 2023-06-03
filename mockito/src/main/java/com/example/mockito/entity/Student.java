package com.example.mockito.entity;

/**
 * @author JuliWolf
 * @date 03.06.2023
 */
public class Student {
  private int id;
  private String stdName;
  private String atdAddress;

  public int getId() {
    return id;
  }

  public String getStdName() {
    return stdName;
  }

  public String getAtdAddress() {
    return atdAddress;
  }

  public Student(int id, String stdName, String atdAddress) {
    this.id = id;
    this.stdName = stdName;
    this.atdAddress = atdAddress;
  }

  @Override
  public String toString() {
    return "Student{" +
        "id=" + id +
        ", stdName='" + stdName + '\'' +
        ", atdAddress='" + atdAddress + '\'' +
        '}';
  }
}
