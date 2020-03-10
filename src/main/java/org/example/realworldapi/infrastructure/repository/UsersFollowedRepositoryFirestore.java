package org.example.realworldapi.infrastructure.repository;

import org.example.realworldapi.domain.model.entity.UsersFollowed;
import org.example.realworldapi.domain.model.entity.UsersFollowedKey;
import org.example.realworldapi.domain.model.repository.UsersFollowedRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsersFollowedRepositoryFirestore implements UsersFollowedRepository {
    @Override
    public boolean isFollowing(String currentUserId, String followedUserId) {
        return false;
    }

    @Override
    public UsersFollowed findByKey(UsersFollowedKey primaryKey) {
        return null;
    }

    @Override
    public UsersFollowed create(UsersFollowed usersFollowed) {
        return null;
    }

    @Override
    public void remove(UsersFollowed usersFollowed) {

    }
}
