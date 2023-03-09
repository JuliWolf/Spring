package com.skb.authorization_books.repositories;

import com.skb.authorization_books.entity.AuthoritiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthoritiesRepository extends JpaRepository<AuthoritiesEntity, Integer> {
  Set<AuthoritiesEntity> findByRole (String role);
}
