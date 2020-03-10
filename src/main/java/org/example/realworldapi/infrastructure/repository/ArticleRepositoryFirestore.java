package org.example.realworldapi.infrastructure.repository;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import lombok.SneakyThrows;
import org.example.realworldapi.domain.model.entity.Article;
import org.example.realworldapi.domain.model.repository.ArticleRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ArticleRepositoryFirestore implements ArticleRepository {

    private static final Logger LOGGER = Logger.getLogger(UserRepositoryFirestore.class);

    private Firestore firestore;

    public ArticleRepositoryFirestore(Firestore firestore) {
        this.firestore = firestore;
    }

    @SneakyThrows
    @Override
    public List<Article> findArticles(int offset, int limit, List<String> tags, List<String> authors, List<String> favorited) {
        //TODO NDK: check if it's returned in the correct order
        //Try to figure out if we could paginate using int offset..
        Query query = firestore.collection("articles").orderBy("createdAt", Query.Direction.DESCENDING).limit(limit);
        return query.get().get().toObjects(Article.class);
    }

    @SneakyThrows
    @Override
    public Article create(Article article) {
        firestore.collection("articles").document(article.getId()).set(article).get();
        LOGGER.debug(String.format("Created new article with id %s", article.getId()));
        return firestore.collection("articles").document(article.getId()).get().get().toObject(Article.class);
    }

    @SneakyThrows
    @Override
    public Article update(Article article) {
        //Explictely set update timestamp to null to use @ServerTimestamp to actually set it
        article.setUpdatedAt(null);

        firestore.collection("articles").document(article.getId()).set(article).get();
        LOGGER.debug(String.format("Updated article with id %s", article.getId()));

        return firestore.collection("articles").document(article.getId()).get().get().toObject(Article.class);
    }

    @SneakyThrows
    @Override
    public boolean existsBySlug(String slug) {
        Query query = firestore.collection("articles").whereEqualTo("slug", slug);
        return !query.get().get().isEmpty();
    }

    @SneakyThrows
    @Override
    public Optional<Article> findBySlug(String slug) {
        Query query = firestore.collection("articles").whereEqualTo("slug", slug);
        return query.get().get().toObjects(Article.class).stream().findFirst();
    }

    @Override
    public void remove(Article article) {
        firestore.collection("articles").document(article.getId()).delete();
    }

    @SneakyThrows
    @Override
    public Optional<Article> findByIdAndSlug(String authorId, String slug) {
        Query query = firestore.collection("articles").whereEqualTo("slug", slug).whereEqualTo("authorId", authorId);
        return query.get().get().toObjects(Article.class).stream().findFirst();
    }

    @SneakyThrows
    @Override
    public List<Article> findMostRecentArticles(String loggedUserId, int offset, int limit) {
        //TODO NDK: check if it's returned in the correct order
        //Try to figure out if we could paginate using int offset..
        Query query = firestore.collection("articles").orderBy("createdAt", Query.Direction.DESCENDING).limit(limit);
        return query.get().get().toObjects(Article.class);
    }

    @Override
    public long count(List<String> tags, List<String> authors, List<String> favorited) {
        //TODO
        return 0;
    }

    @Override
    public long count(String loggedUserId) {
        //TODO
        return 0;
    }
}
