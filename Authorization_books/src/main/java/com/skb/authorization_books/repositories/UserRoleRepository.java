package com.skb.authorization_books.repositories;

import com.skb.authorization_books.entity.UserEntity;
import com.skb.authorization_books.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {
  UserEntity findByUserEntity (UserEntity userEntity);
}
