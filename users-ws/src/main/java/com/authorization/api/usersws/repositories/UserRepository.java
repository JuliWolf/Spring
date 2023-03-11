package com.authorization.api.usersws.repositories;

import com.authorization.api.usersws.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
  UserEntity findByUsername (String username);
}
