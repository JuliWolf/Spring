package com.skb.authorization_books.entity;

import com.skb.authorization_books.entity.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRoleEntity {
  @Column(name = "user_role_id")
  @Id
  private Integer userRoleId;

  @Column(name = "role")
  private String role;

  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL
  )
  @JoinColumn(name = "username", nullable = false)
  private UserEntity userEntity;

  public Integer getUserRoleId() {
    return userRoleId;
  }

  public void setUserRoleId(Integer userRoleId) {
    this.userRoleId = userRoleId;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public UserEntity getUserEntity() {
    return userEntity;
  }

  public void setUserEntity(UserEntity userEntity) {
    this.userEntity = userEntity;
  }

  public UserRoleEntity() {
  }

  public UserRoleEntity(Integer userRoleId, String role) {
    this.userRoleId = userRoleId;
    this.role = role;
  }

  @Override
  public String toString() {
    return "UserRoleEntity{" +
        "userRoleId=" + userRoleId +
        ", role='" + role + '\'' +
        ", userEntity=" + userEntity +
        '}';
  }
}
