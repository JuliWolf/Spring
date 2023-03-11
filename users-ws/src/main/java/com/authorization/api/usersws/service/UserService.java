package com.authorization.api.usersws.service;

import com.authorization.api.usersws.entities.AuthoritiesEntity;
import com.authorization.api.usersws.entities.UserEntity;
import com.authorization.api.usersws.repositories.AuthoritiesRepository;
import com.authorization.api.usersws.repositories.UserRepository;
import com.authorization.api.usersws.userDetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthoritiesRepository authoritiesRepository;

  public UserService() {}

  public User getUserByUsername (String username) {
    UserEntity userEntity = userRepository.findByUsername(username);

    if (userEntity != null) {
      return createUserFromUserEntity(userEntity);
    }

    return null;
  }

  private User createUserFromUserEntity(UserEntity userEntity) {
    User user = new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.getEnabled());

    // Fetch authorities from authorities table
    Stream<Stream<String>> streamStreamAuths = userEntity.getUserRoles()
        .stream()
        // ['ROLE_ADMIN']
        .map(userRoleEntity -> {
          // ['ADD_BOOK', 'CREATE_BOOK']
          Set<AuthoritiesEntity> authoritiesEntities = authoritiesRepository.findByRole(userRoleEntity.getRole());
          return authoritiesEntities.stream()
              .map(entity -> entity.getAuthority());
        });

    // Flatten the stream of streams to get the set of authorities
    // ['ADD_BOOK', 'CREATE_BOOK']
    Set<String> authorities = streamStreamAuths
        .flatMap(authStream -> authStream)
        .collect(Collectors.toSet());

    // add the Role (from the user_role table) as authorities
    // because UserDetails does not support adding Role separately as it does not have any setRole
    userEntity.getUserRoles()
        .stream()
        // ['ROLE_ADMIN']
        // authorities = ['ROLE_ADMIN', 'ADD_BOOK', 'CREATE_BOOK']
        .forEach(userRoleEntity -> authorities.add(userRoleEntity.getRole()));

    // Set all authorities for the User
    user.setAuthorities(authorities
        .stream()
        .map(auth -> new SimpleGrantedAuthority(auth))
        .collect(Collectors.toSet()));

    return user;
  }
}
