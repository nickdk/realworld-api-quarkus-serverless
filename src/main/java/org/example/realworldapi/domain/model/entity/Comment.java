package org.example.realworldapi.domain.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Comment {

  private String id;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String body;
  private Article article;
  private User author;
}
