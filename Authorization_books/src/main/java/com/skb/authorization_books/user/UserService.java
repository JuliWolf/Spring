package com.skb.authorization_books.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthoritiesRepository authoritiesRepository;

  //  private User user = null;

  public UserService() {
//    user = new User("myusername", "$2a$12$Q3ucJL8PMXIZP4vOqWtIN.W6wxg/IrdINNm2fCXZekwJdQoFt1hzS", true);
  }

  public User getUserByUsername (String username) {
//    Optional<User> userOptional = UserRepositoryService.findByUsername(username);
//    return userOptional.orElse(null);

    UserEntity userEntity = userRepository.findByUsername(username);

    if (userEntity != null) {
      return createUserFromUserEntity(userEntity);
    }

    return null;
  }

  private User createUserFromUserEntity(UserEntity userEntity) {
    User user = new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.getEnabled());
    user.setAuthorities(userEntity.getAuthoritiesEntities()
        .stream()
        .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
        .collect(Collectors.toSet())
    );

    return user;
  }
}
