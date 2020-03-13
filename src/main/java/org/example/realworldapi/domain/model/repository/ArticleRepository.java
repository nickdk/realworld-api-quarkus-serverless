package org.example.realworldapi.domain.model.repository;

import org.example.realworldapi.domain.model.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    List<Article> findArticles(
            int offset, int limit, List<String> tags, List<String> authors, List<String> favorited);

    Article create(Article article);

    Article update(Article article);

    boolean existsBySlug(String slug);

    Optional<Article> findBySlug(String slug);

    void remove(Article article);

    Optional<Article> findByIdAndSlug(String authorId, String slug);

    List<Article> findMostRecentArticles(String loggedUserId, int offset, int limit);

    long count(List<String> tags, List<String> authors, List<String> favorited);

    long count(String loggedUserId);
}
