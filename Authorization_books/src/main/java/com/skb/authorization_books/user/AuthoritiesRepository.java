package com.skb.authorization_books.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritiesRepository extends JpaRepository<AuthoritiesEntity, Integer> {
  UserEntity findByUserEntity (UserEntity userEntity);
}
