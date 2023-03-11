package com.authorization.api.usersws.repositories;

import com.authorization.api.usersws.entities.AuthoritiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthoritiesRepository extends JpaRepository<AuthoritiesEntity, Integer> {
  Set<AuthoritiesEntity> findByRole (String role);
}
