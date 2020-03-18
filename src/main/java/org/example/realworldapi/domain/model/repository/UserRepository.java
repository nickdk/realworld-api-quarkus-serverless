package org.example.realworldapi.domain.model.repository;

import org.example.realworldapi.domain.model.entity.User;

import java.util.Optional;

public interface UserRepository {
    User create(User user);

    Optional<User> findUserByEmail(String email);

    boolean existsBy(String field, String value);

    Optional<User> findUserById(String id);

    boolean existsUsername(String excludeId, String username);

    boolean existsEmail(String excludeId, String email);

    Optional<User> findUserByUsername(String username);

    User mergeUpdateableFields(User user);

    User mergeUpdateableFieldsIncludingPassword(User user);

    User overwriteUser(User user);
}
