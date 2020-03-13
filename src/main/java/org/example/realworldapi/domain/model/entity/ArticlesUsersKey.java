package org.example.realworldapi.domain.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class ArticlesUsersKey implements Serializable {

    private Article article;
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ArticlesUsersKey that = (ArticlesUsersKey) o;
        return Objects.equals(user, that.user) && Objects.equals(article, that.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, article);
    }
}
