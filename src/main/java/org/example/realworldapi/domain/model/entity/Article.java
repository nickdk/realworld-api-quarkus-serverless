package org.example.realworldapi.domain.model.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Article {

  private String id;
  private String slug;
  private String title;
  private String description;
  private String body;
  @ServerTimestamp
  private Timestamp createdAt;
  @ServerTimestamp
  private Timestamp updatedAt;
  private User author;
  private List<Comment> comments;
  private List<String> tagList;
  private List<String> favorites;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    Article that = (Article) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
