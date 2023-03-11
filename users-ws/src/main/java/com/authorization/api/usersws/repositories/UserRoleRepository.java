package com.authorization.api.usersws.repositories;

import com.authorization.api.usersws.entities.UserEntity;
import com.authorization.api.usersws.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {
  UserEntity findByUserEntity (UserEntity userEntity);
}
