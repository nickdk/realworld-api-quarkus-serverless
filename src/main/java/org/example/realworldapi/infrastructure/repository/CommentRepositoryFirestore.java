package org.example.realworldapi.infrastructure.repository;

import org.example.realworldapi.domain.model.entity.Comment;
import org.example.realworldapi.domain.model.repository.CommentRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CommentRepositoryFirestore implements CommentRepository {
    @Override
    public Comment create(Comment comment) {
        return null;
    }

    @Override
    public Optional<Comment> findComment(String slug, String commentId, String authorId) {
        return Optional.empty();
    }

    @Override
    public void remove(Comment comment) {

    }

    @Override
    public List<Comment> findArticleComments(String articleId) {
        return null;
    }
}
