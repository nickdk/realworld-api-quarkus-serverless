package org.example.realworldapi.application;

import org.example.realworldapi.application.data.ProfileData;
import org.example.realworldapi.domain.model.builder.ProfileBuilder;
import org.example.realworldapi.domain.model.entity.User;
import org.example.realworldapi.domain.model.entity.UsersFollowed;
import org.example.realworldapi.domain.model.entity.UsersFollowedKey;
import org.example.realworldapi.domain.model.repository.UserRepository;
import org.example.realworldapi.domain.service.ProfilesService;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class ProfilesServiceImpl implements ProfilesService {

    private UserRepository userRepository;

    public ProfilesServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ProfileData getProfile(String username, String loggedUserId) {
        User existentUser = userRepository.findUserByUsername(username).orElseThrow(NotFoundException::new);

        ProfileBuilder profileBuilder = new ProfileBuilder().fromUser(existentUser);
        if (loggedUserId != null) {
            profileBuilder.following(existentUser.getFollowedBy().contains(loggedUserId));
        }

        return profileBuilder.build();
    }

    @Override
    @Transactional
    public ProfileData follow(String loggedUserId, String username) {
        User loggedUser = userRepository.findUserById(loggedUserId).orElseThrow(NotFoundException::new);
        User userToFollow = userRepository.findUserByUsername(username).orElseThrow(NotFoundException::new);

        if(!userToFollow.getFollowedBy().contains(loggedUserId)) {
            userToFollow.getFollowedBy().add(loggedUserId);
            userRepository.overwriteUser(userToFollow);
        }

        if(!loggedUser.getFollowing().contains(userToFollow.getId())) {
            loggedUser.getFollowing().add(userToFollow.getId());
            userRepository.overwriteUser(loggedUser);
        }

        return getProfile(username, loggedUserId);
    }

    @Override
    @Transactional
    public ProfileData unfollow(String loggedUserId, String username) {
        User loggedUser = userRepository.findUserById(loggedUserId).orElseThrow(NotFoundException::new);
        User userToUnfollow = userRepository.findUserByUsername(username).orElseThrow(NotFoundException::new);

        if(userToUnfollow.getFollowedBy().contains(loggedUserId)) {
            userToUnfollow.getFollowedBy().remove(loggedUserId);
            userRepository.overwriteUser(userToUnfollow);
        }

        if(loggedUser.getFollowing().contains(userToUnfollow.getId())) {
            loggedUser.getFollowing().remove(userToUnfollow.getId());
            userRepository.overwriteUser(loggedUser);
        }
        return getProfile(username, loggedUserId);
    }

    private UsersFollowed getUsersFollowed(User user, User followed) {
        UsersFollowedKey primaryKey = getUsersFollowedKey(user, followed);
        UsersFollowed usersFollowed = new UsersFollowed();
        usersFollowed.setPrimaryKey(primaryKey);
        return usersFollowed;
    }

    private UsersFollowedKey getUsersFollowedKey(User user, User followed) {
        UsersFollowedKey primaryKey = new UsersFollowedKey();
        primaryKey.setUser(user);
        primaryKey.setFollowed(followed);
        return primaryKey;
    }
}
