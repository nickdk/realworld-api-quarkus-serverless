package org.example.realworldapi.domain.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class UsersFollowedKey implements Serializable {

    private User user;
    private User followed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UsersFollowedKey that = (UsersFollowedKey) o;
        return Objects.equals(user, that.user) && Objects.equals(followed, that.followed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, followed);
    }
}
