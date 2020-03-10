package org.example.realworldapi.domain.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ArticlesTags {
  private ArticlesTagsKey primaryKey;

  public ArticlesTags(ArticlesTagsKey primaryKey) {
    this.primaryKey = primaryKey;
  }

  private Article article;
  private Tag tag;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    ArticlesTags that = (ArticlesTags) o;
    return Objects.equals(primaryKey, that.primaryKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(primaryKey);
  }
}
