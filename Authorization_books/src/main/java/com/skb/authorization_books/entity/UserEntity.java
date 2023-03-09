package com.skb.authorization_books.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
  @Id
  @Column(name="username")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String username;

  @Column(name="password")
  private String password;

  @Column(name="enabled")
  private Boolean enabled;

  @OneToMany(
      mappedBy = "userEntity",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL
  )
  private Set<UserRoleEntity> userRoles;

  @Override
  public String toString() {
    return "UserEntity{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", enabled=" + enabled +
        '}';
  }
}
