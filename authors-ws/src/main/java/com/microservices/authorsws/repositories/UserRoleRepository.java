package com.microservices.authorsws.repositories;

import com.microservices.authorsws.entities.UserEntity;
import com.microservices.authorsws.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {
  UserEntity findByUserEntity (UserEntity userEntity);
}
