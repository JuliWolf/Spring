package com.example.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author JuliWolf
 * @date 14.08.2023
 */
@Entity
@Table(name="boxes")
public class Box {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="boxId")
  private int boxId;

  @Column(name="boxName")
  private String boxName;

  @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name="boxId")
  private List<Ball> balls;

  public List<Ball> getBalls() {
    return balls;
  }

  public void setBalls(List<Ball> balls) {
    this.balls = balls;
  }

  public int getBoxId() {
    return boxId;
  }

  public void setBoxId(int boxId) {
    this.boxId = boxId;
  }

  public String getBoxName() {
    return boxName;
  }

  public void setBoxName(String boxName) {
    this.boxName = boxName;
  }

  public Box() {
  }

  public Box(String boxName, List<Ball> balls) {
    this.boxName = boxName;
    this.balls = balls;
  }

  @Override
  public String toString() {
    return "Box{" +
        "boxId=" + boxId +
        ", boxName='" + boxName + '\'' +
        '}';
  }
}
