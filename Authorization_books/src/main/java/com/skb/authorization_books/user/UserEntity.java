package com.skb.authorization_books.user;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="users")
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
  private Set<AuthoritiesEntity> authoritiesEntities;

  public Set<AuthoritiesEntity> getAuthoritiesEntities() {
    return authoritiesEntities;
  }

  public void setAuthoritiesEntities(Set<AuthoritiesEntity> authoritiesEntities) {
    this.authoritiesEntities = authoritiesEntities;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public UserEntity() {
  }

  public UserEntity(String username, String password, Boolean enabled) {
    this.username = username;
    this.password = password;
    this.enabled = enabled;
  }

  @Override
  public String toString() {
    return "UserEntity{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", enabled=" + enabled +
        '}';
  }
}
