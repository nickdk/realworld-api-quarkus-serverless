package org.example.realworldapi.infrastructure.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.application.data.ArticleData;
import org.example.realworldapi.application.data.ProfileData;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonRootName("article")
@RegisterForReflection
public class ArticleResponse {

  private String slug;
  private String title;
  private String description;
  private String body;
  private List<String> tagList;

  private String createdAt;
  private String updatedAt;

  private boolean favorited;
  private long favoritesCount;
  private ProfileData author;

  public ArticleResponse(ArticleData articleData) {
    this.slug = articleData.getSlug();
    this.title = articleData.getTitle();
    this.description = articleData.getDescription();
    this.body = articleData.getBody();
    this.tagList = articleData.getTagList();
    this.createdAt = articleData.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    this.updatedAt = articleData.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    this.favorited = articleData.isFavorited();
    this.favoritesCount = articleData.getFavoritesCount();
    this.author = articleData.getAuthor();
  }
}
