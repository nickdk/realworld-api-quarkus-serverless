package org.example.realworldapi.domain.model.repository;

import org.example.realworldapi.domain.model.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
  User create(User user);

  Optional<User> findUserByEmail(String email);

  boolean existsBy(String field, String value);

  Optional<User> findUserById(String id);

  boolean existsUsername(String excludeId, String username);

  boolean existsEmail(String excludeId, String email);

  Optional<User> findByUsernameOptional(String username);
}
