package org.example.realworldapi.application;

import com.google.cloud.Timestamp;
import org.example.realworldapi.application.data.ArticleData;
import org.example.realworldapi.application.data.ArticlesData;
import org.example.realworldapi.application.data.CommentData;
import org.example.realworldapi.application.data.ProfileData;
import org.example.realworldapi.domain.model.entity.*;
import org.example.realworldapi.domain.model.exception.ArticleNotFoundException;
import org.example.realworldapi.domain.model.exception.UserNotFoundException;
import org.example.realworldapi.domain.model.provider.SlugProvider;
import org.example.realworldapi.domain.model.repository.ArticleRepository;
import org.example.realworldapi.domain.model.repository.TagRepository;
import org.example.realworldapi.domain.model.repository.UserRepository;
import org.example.realworldapi.domain.service.ArticlesService;
import org.example.realworldapi.domain.service.ProfilesService;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ArticlesServiceImpl implements ArticlesService {

    private static final int DEFAULT_LIMIT = 20;

    private UserRepository userRepository;
    private TagRepository tagRepository;
    private ArticleRepository articleRepository;
    private ProfilesService profilesService;
    private SlugProvider slugProvider;

    public ArticlesServiceImpl(
            UserRepository userRepository,
            TagRepository tagRepository,
            ArticleRepository articleRepository,
            ProfilesService profilesService,
            SlugProvider slugProvider) {
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.articleRepository = articleRepository;
        this.profilesService = profilesService;
        this.slugProvider = slugProvider;
    }

    @Override
    @Transactional
    public ArticlesData findRecentArticles(String loggedUserId, int offset, int limit) {

        List<Article> articles =
                articleRepository.findMostRecentArticles(loggedUserId, offset, getLimit(limit));

        long articlesCount = articleRepository.count(loggedUserId);

        return new ArticlesData(toResultList(articles, loggedUserId), articlesCount);
    }

    @Override
    @Transactional
    public ArticlesData findArticles(
            int offset,
            int limit,
            String loggedUserId,
            List<String> tags,
            List<String> authors,
            List<String> favorited) {

        List<Article> articles =
                articleRepository.findArticles(offset, getLimit(limit), tags, authors, favorited);

        long articlesCount = articleRepository.count(tags, authors, favorited);

        return new ArticlesData(toResultList(articles, loggedUserId), articlesCount);
    }

    @Override
    @Transactional
    public ArticleData create(
            String title, String description, String body, List<String> tagList, String authorId) {
        Article article = createArticle(title, description, body, authorId, tagList);
        createTags(tagList);
        return getArticle(article, authorId);
    }

    @Override
    @Transactional
    public ArticleData findBySlug(String slug) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        return getArticle(article, null);
    }

    @Override
    @Transactional
    public ArticleData update(
            String slug, String title, String description, String body, String authorId) {

        Article article = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        if (isPresent(title)) {
            configTitle(title, article);
        }

        if (isPresent(description)) {
            article.setDescription(description);
        }

        if (isPresent(body)) {
            article.setBody(body);
        }

        return getArticle(article, authorId);
    }

    @Override
    @Transactional
    public void delete(String slug, String authorId) {
        Article article =
                articleRepository
                        .findByIdAndSlug(authorId, slug)
                        .orElseThrow(ArticleNotFoundException::new);
        articleRepository.remove(article);
    }

    @Override
    @Transactional
    public List<CommentData> findCommentsBySlug(String slug, String loggedUserId) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        return article.getComments().stream()
                .map(
                        comment -> {
                            ProfileData author;
                            try {
                                author = profilesService.getProfile(comment.getAuthor().getUsername(), loggedUserId);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            return getComment(comment, author);
                        })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentData createComment(String slug, String body, String commentAuthorId) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        User author = userRepository.findUserById(commentAuthorId).orElseThrow(UserNotFoundException::new);
        Comment comment = createComment(body, article, author);

        article.getComments().add(comment);
        articleRepository.update(article);

        ProfileData authorProfile = profilesService.getProfile(author.getUsername(), author.getId());
        return getComment(comment, authorProfile);
    }

    @Override
    @Transactional
    public void deleteComment(String slug, String commentId, String loggedUserId) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        article.getComments().removeIf(comment -> comment.getId().equals(commentId));
        articleRepository.update(article);
    }

    @Override
    @Transactional
    public ArticleData favoriteArticle(String slug, String loggedUserId) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        if (!article.getFavorites().contains(loggedUserId)) {
            article.getFavorites().add(loggedUserId);
            return getArticle(articleRepository.update(article), loggedUserId);
        }
        return getArticle(article, loggedUserId);
    }

    @Override
    @Transactional
    public ArticleData unfavoriteArticle(String slug, String loggedUserId) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        if (article.getFavorites().contains(loggedUserId)) {
            article.getFavorites().remove(loggedUserId);
            return getArticle(articleRepository.update(article), loggedUserId);
        }

        return getArticle(article, loggedUserId);
    }

    private Comment createComment(String body, Article article, User author) {
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString());
        comment.setAuthor(author);
        comment.setBody(body);

        //@ServerTimestamp is currently not supported by the Firestore sdk in arrays
        Timestamp now = Timestamp.now();
        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);
        return comment;
    }

    private CommentData getComment(Comment comment, ProfileData authorProfile) {
        return new CommentData(
                comment.getId(),
                LocalDateTime.ofInstant(comment.getCreatedAt().toDate().toInstant(), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(comment.getUpdatedAt().toDate().toInstant(), ZoneId.systemDefault()),
                comment.getBody(),
                authorProfile);
    }

    private void configTitle(String title, Article article) {
        configSlug(title, article);
        article.setTitle(title);
    }

    private Article createArticle(String title, String description, String body, String userId, List<String> tagList) {
        Article article = new Article();
        configTitle(title, article);
        article.setId(UUID.randomUUID().toString());
        article.setDescription(description);
        article.setBody(body);
        article.setTagList(tagList);
        article.setFavorites(Collections.emptyList());
        article.setComments(Collections.emptyList());
        article.setAuthor(userRepository.findUserById(userId).orElseThrow(UserNotFoundException::new));
        return articleRepository.create(article);
    }

    private void configSlug(String title, Article article) {
        String slug = slugProvider.slugify(title);
        if (articleRepository.existsBySlug(slug)) {
            slug += UUID.randomUUID().toString().toString();
        }
        article.setSlug(slug);
    }

    private void createTags(List<String> tagList) {
        tagList.forEach(
                tagName -> {
                    Optional<Tag> tagOptional = tagRepository.findByName(tagName);

                    tagOptional.orElseGet(() -> createTag(tagName));
                });
    }

    private Tag createTag(String tagName) {
        return tagRepository.create(new Tag(tagName));
    }

    private ArticlesTags createTags(Article article, Tag tag) {
        ArticlesTagsKey articlesTagsKey = new ArticlesTagsKey(article, tag);
        return new ArticlesTags(articlesTagsKey);
    }

    private List<ArticleData> toResultList(List<Article> articles, String loggedUserId) {
        return articles.stream()
                .map(article -> getArticle(article, loggedUserId))
                .collect(Collectors.toList());
    }

    private ArticleData getArticle(Article article, String loggedUserId) {
        boolean isFavorited = false;

        if (loggedUserId != null) {
            isFavorited = article.getFavorites().contains(loggedUserId);
        }

        long favoritesCount = article.getFavorites().size();

        ProfileData author =
                profilesService.getProfile(article.getAuthor().getUsername(), loggedUserId);

        return new ArticleData(
                article.getSlug(),
                article.getTitle(),
                article.getDescription(),
                article.getBody(),
                article.getTagList(),
                isFavorited,
                favoritesCount,
                LocalDateTime.ofInstant(article.getCreatedAt().toDate().toInstant(), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(article.getUpdatedAt().toDate().toInstant(), ZoneId.systemDefault()),
                author);
    }

    private int getLimit(int limit) {
        return limit > 0 ? limit : DEFAULT_LIMIT;
    }

    private boolean isPresent(String value) {
        return value != null && !value.isEmpty();
    }
}
