package org.example.realworldapi.domain.model.security.service;

import io.quarkus.test.junit.QuarkusTest;
import org.example.realworldapi.application.ProfilesServiceImpl;
import org.example.realworldapi.application.data.ProfileData;
import org.example.realworldapi.domain.model.builder.UserBuilder;
import org.example.realworldapi.domain.model.entity.User;
import org.example.realworldapi.domain.model.repository.UserRepository;
import org.example.realworldapi.domain.service.ProfilesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
public class ProfilesServiceImplTest {

    private UserRepository userRepository;
    private ProfilesService profilesService;

    @BeforeEach
    private void beforeEach() {
        userRepository = mock(UserRepository.class);
        profilesService = new ProfilesServiceImpl(userRepository);
    }

    @Test
    public void
    givenValidUsernameAndNullForExistentUserLoggedUserId_shouldReturnProfileWithFollowingFieldFalse() {
        String username = "user1";
        String loggedUserId = "cee679d7-a6de-470e-a661-a4778b7923d8";

        User existingUser =
                new UserBuilder().id(UUID.randomUUID().toString()).username(username).bio("bio").image("image").build();

        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(existingUser));

        ProfileData result = profilesService.getProfile(username, loggedUserId);

        Assertions.assertEquals(existingUser.getUsername(), result.getUsername());
        Assertions.assertEquals(existingUser.getBio(), result.getBio());
        Assertions.assertEquals(existingUser.getImage(), result.getImage());
        Assertions.assertFalse(result.isFollowing());
    }

    @Test
    public void
    givenValidUsernameAndLoggedUserIdWithFollowers_shouldReturnProfileWithFollowingFieldTrue() {
        String username = "user1";
        String loggedUserId = "cee679d7-a6de-470e-a661-a4778b7923d8";

        User existingUser =
                new UserBuilder().id(UUID.randomUUID().toString()).username(username).bio("bio").image("image").build();

        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(existingUser));

        ProfileData result = profilesService.getProfile(username, loggedUserId);

        Assertions.assertEquals(existingUser.getUsername(), result.getUsername());
        Assertions.assertEquals(existingUser.getBio(), result.getBio());
        Assertions.assertEquals(existingUser.getImage(), result.getImage());
        Assertions.assertTrue(result.isFollowing());
    }
}
