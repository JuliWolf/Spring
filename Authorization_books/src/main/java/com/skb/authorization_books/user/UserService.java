package com.skb.authorization_books.user;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  private User user = null;

  public UserService() {
//    user = new User("myusername", "$2a$12$Q3ucJL8PMXIZP4vOqWtIN.W6wxg/IrdINNm2fCXZekwJdQoFt1hzS", true);
  }

  public User getUserByUsername (String username) {
    Optional<User> userOptional = UserRepository.findByUsername(username);

    return userOptional.orElse(null);
  }
}
