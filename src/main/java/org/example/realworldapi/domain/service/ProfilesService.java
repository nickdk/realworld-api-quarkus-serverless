package org.example.realworldapi.domain.service;

import org.example.realworldapi.application.data.ProfileData;

import java.util.UUID;

public interface ProfilesService {
  ProfileData getProfile(String username, String loggedUserId);

  ProfileData follow(String loggedUserId, String username);

  ProfileData unfollow(String loggedUserId, String username);
}
