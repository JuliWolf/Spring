package com.example.mockito.entity;

import jakarta.persistence.*;

/**
 * @author JuliWolf
 * @date 03.06.2023
 */
@Entity
@Table(name="STUDENT")
public class Student {
  @Id
  @GeneratedValue
  private int id;

  @Column(name="stdName")
  private String stdName;

  @Column(name="stdAddress")
  private String stdAddress;

  @Transient
  private int myValue;

  public Student() {

  }

  public int getId() {
    return id;
  }

  public String getStdName() {
    return stdName;
  }

  public String getStdAddress() {
    return stdAddress;
  }

  public Student(int id, String stdName, String stdAddress) {
    this.id = id;
    this.stdName = stdName;
    this.stdAddress = stdAddress;
  }

  @Override
  public String toString() {
    return "Student{" +
        "id=" + id +
        ", stdName='" + stdName + '\'' +
        ", atdAddress='" + stdAddress + '\'' +
        '}';
  }
}
