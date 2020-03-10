package org.example.realworldapi.domain.service;

import org.example.realworldapi.domain.model.entity.User;

import java.util.UUID;

public interface UsersService {
  User create(String username, String email, String password);

  User login(String email, String password);

  User findById(String id);

  User update(User user);

  User findByUsername(String username);
}
