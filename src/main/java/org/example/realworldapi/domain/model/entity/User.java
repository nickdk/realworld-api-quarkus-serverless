package org.example.realworldapi.domain.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class User {

  private String id;

  private String username;
  private String bio;
  private String image;
  private String password;
  private String email;
  private String token;
  private List<Article> articles;
  private List<UsersFollowed> following;
  private List<UsersFollowed> followedBy;

  public User(String id, String username, String bio, String image) {
    this.id = id;
    this.username = username;
    this.bio = bio;
    this.image = image;
  }
}
