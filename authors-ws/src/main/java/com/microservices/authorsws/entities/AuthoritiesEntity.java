package com.microservices.authorsws.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "authorities")
public class AuthoritiesEntity {
  @Column(name = "authority_id")
  @Id
  private Integer authorityId;

  @Column(name = "authority")
  private String authority;

  @Column(name= "role")
  private String role;

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
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

  public AuthoritiesEntity(Integer authorityId, String authority, String role) {
    this.authorityId = authorityId;
    this.authority = authority;
    this.role = role;
  }

  @Override
  public String toString() {
    return "AuthoritiesEntity{" +
        "authorityId=" + authorityId +
        ", authority='" + authority + '\'' +
        ", role='" + role + '\'' +
        '}';
  }
}
