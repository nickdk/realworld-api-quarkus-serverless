package org.example.realworldapi.domain.model.repository;

import org.example.realworldapi.domain.model.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository {
  Comment create(Comment comment);

  Optional<Comment> findComment(String slug, String commentId, String authorId);

  void remove(Comment comment);

  List<Comment> findArticleComments(String articleId);
}
