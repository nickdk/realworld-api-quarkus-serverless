package org.example.realworldapi.domain.service;

import org.example.realworldapi.application.data.ArticleData;
import org.example.realworldapi.application.data.ArticlesData;
import org.example.realworldapi.application.data.CommentData;

import java.util.List;

public interface ArticlesService {
  ArticlesData findRecentArticles(String loggedUserId, int offset, int limit);

  ArticlesData findArticles(
      int offset,
      int limit,
      String loggedUserId,
      List<String> tags,
      List<String> authors,
      List<String> favorited);

  ArticleData create(
      String title, String description, String body, List<String> tagList, String authorId);

  ArticleData findBySlug(String slug);

  ArticleData update(String slug, String title, String description, String body, String authorId);

  void delete(String slug, String authorId);

  List<CommentData> findCommentsBySlug(String slug, String loggedUserId);

  CommentData createComment(String slug, String body, String commentAuthorId);

  void deleteComment(String slug, String commentId, String loggedUserId);

  ArticleData favoriteArticle(String slug, String loggedUserId);

  ArticleData unfavoriteArticle(String slug, String loggedUserId);
}
