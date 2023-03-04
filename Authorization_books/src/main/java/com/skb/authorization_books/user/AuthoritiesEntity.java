package com.skb.authorization_books.user;

import jakarta.persistence.*;

@Entity
@Table(name = "authorities")
public class AuthoritiesEntity {
  @Column(name = "authority_id")
  @Id
  private Integer authorityId;

  @Column(name = "authority")
  private String authority;

  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL
  )
  @JoinColumn(name = "username", nullable = false)
  private UserEntity userEntity;

  public UserEntity getUserEntity() {
    return userEntity;
  }

  public void setUserEntity(UserEntity userEntity) {
    this.userEntity = userEntity;
  }

  public Integer getAuthorityId() {
    return authorityId;
  }

  public void setAuthorityId(Integer authorityId) {
    this.authorityId = authorityId;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  public AuthoritiesEntity() {
  }

  public AuthoritiesEntity(Integer authorityId, String authority, UserEntity userEntity) {
    this.authorityId = authorityId;
    this.authority = authority;
    this.userEntity = userEntity;
  }

  @Override
  public String toString() {
    return "AuthoritiesEntity{" +
        "authorityId=" + authorityId +
        ", authority='" + authority + '\'' +
        ", userEntity=" + userEntity +
        '}';
  }
}
