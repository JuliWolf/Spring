package com.example.entity;

import javax.persistence.*;

/**
 * @author JuliWolf
 * @date 14.08.2023
 */

@Entity
@Table(name="balls")
public class Ball {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="ballId")
  private int ballId;

  @Column(name="ballName")
  private String ballName;

  @ManyToOne(cascade = {
      CascadeType.DETACH,
      CascadeType.MERGE,
      CascadeType.PERSIST,
      CascadeType.REFRESH
  })
  @JoinColumn(name="boxId")
  private Box box;

  public int getBallId() {
    return ballId;
  }

  public void setBallId(int ballId) {
    this.ballId = ballId;
  }

  public String getBallName() {
    return ballName;
  }

  public void setBallName(String ballName) {
    this.ballName = ballName;
  }

  public Box getBox() {
    return box;
  }

  public void setBox(Box box) {
    this.box = box;
  }

  public Ball() {
  }

  public Ball(String ballName, Box box) {
    this.ballName = ballName;
    this.box = box;
  }

  @Override
  public String toString() {
    return "Ball{" +
        "ballId=" + ballId +
        ", ballName='" + ballName + '\'' +
        ", box=" + box +
        '}';
  }
}
