package org.example.realworldapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;
import org.example.realworldapi.domain.model.builder.ArticleBuilder;
import org.example.realworldapi.domain.model.entity.*;
import org.example.realworldapi.domain.model.provider.TokenProvider;
import org.example.realworldapi.util.UserUtils;
import org.junit.jupiter.api.BeforeEach;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

public class AbstractIntegrationTest extends DatabaseIntegrationTest {

    @Inject
    protected ObjectMapper objectMapper;
    @Inject
    protected TokenProvider tokenProvider;
    @Inject
    protected Slugify slugify;

    @BeforeEach
    public void beforeEach() {
        clear();
    }

    protected User createUser(
            String username, String email, String bio, String image, String password) {
        User user = UserUtils.create(username, email, password, bio, image);
        firestore.collection("users").document(user.getId()).set(user);
        user.setToken(tokenProvider.createUserToken(user.getId()));
        return user;
    }

    protected void follow(User currentUser, User... followers) {
        //TODO NDK
//        User user = entityManager.find(User.class, currentUser.getId());
        User user = null;

        for (User follower : followers) {
            UsersFollowedKey key = new UsersFollowedKey();
            key.setUser(user);
            key.setFollowed(follower);

            UsersFollowed usersFollowed = new UsersFollowed();
            usersFollowed.setPrimaryKey(key);
            //TODO NDK persist
        }
    }

    protected Tag createTag(String name) {
        Tag tag = new Tag(name);
        //TODO NDK persist
        return tag;
    }

    protected List<ArticlesTags> createArticlesTags(List<Article> articles, Tag... tags) {
        List<ArticlesTags> resultList = new LinkedList<>();

        for (Article article : articles) {

//        Article managedArticle = entityManager.find(Article.class, article.getId());
            //TODO NDK
            Article managedArticle = null;

            for (Tag tag : tags) {
//          Tag managedTag = entityManager.find(Tag.class, tag.getId());

                Tag managedTag = null;

                ArticlesTagsKey articlesTagsKey = new ArticlesTagsKey();
                articlesTagsKey.setArticle(managedArticle);
                articlesTagsKey.setTag(managedTag);

                ArticlesTags articlesTags = new ArticlesTags();
                articlesTags.setPrimaryKey(articlesTagsKey);

                //TODO NDK persist
//          entityManager.persist(articlesTags);
                resultList.add(articlesTags);
            }
        }

        return resultList;
    }

    protected List<Article> createArticles(
            User author, String title, String description, String body, int quantity) {

        List<Article> articles = new LinkedList<>();

        for (int articleIndex = 0; articleIndex < quantity; articleIndex++) {
            articles.add(
                    createArticle(
                            author,
                            title + "_" + articleIndex,
                            description + "_" + articleIndex,
                            body + "_" + articleIndex));
        }

        return articles;
    }

    protected Article createArticle(User author, String title, String description, String body) {
        Article article =
                new ArticleBuilder()
                        .title(title)
                        .slug(slugify.slugify(title))
                        .description(description)
                        .body(body)
                        .author(author)
                        .build();
        //TODO NDK persist
        return article;
    }

    protected ArticlesUsers favorite(Article article, User user) {
        ArticlesUsers articlesUsers = getArticlesUsers(article, user);
        //TODO NDK persist
        return articlesUsers;
    }

    protected Comment createComment(User author, Article article, String body) {
        Comment comment = new Comment();
        comment.setBody(body);
        comment.setAuthor(author);
        //TODO NDK persist
        return comment;
    }

    ;

    private ArticlesUsers getArticlesUsers(Article article, User loggedUser) {
        ArticlesUsersKey articlesUsersKey = getArticlesUsersKey(article, loggedUser);
        ArticlesUsers articlesUsers = new ArticlesUsers();
        articlesUsers.setPrimaryKey(articlesUsersKey);
        return articlesUsers;
    }

    private ArticlesUsersKey getArticlesUsersKey(Article article, User loggedUser) {
        ArticlesUsersKey articlesUsersKey = new ArticlesUsersKey();
        articlesUsersKey.setArticle(article);
        articlesUsersKey.setUser(loggedUser);
        return articlesUsersKey;
    }
}
