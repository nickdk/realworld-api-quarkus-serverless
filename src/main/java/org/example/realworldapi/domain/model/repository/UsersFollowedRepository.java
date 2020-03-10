package org.example.realworldapi.domain.model.repository;

import org.example.realworldapi.domain.model.entity.UsersFollowed;
import org.example.realworldapi.domain.model.entity.UsersFollowedKey;

import java.util.UUID;

public interface UsersFollowedRepository {

  boolean isFollowing(String currentUserId, String followedUserId);

  UsersFollowed findByKey(UsersFollowedKey primaryKey);

  UsersFollowed create(UsersFollowed usersFollowed);

  void remove(UsersFollowed usersFollowed);
}
