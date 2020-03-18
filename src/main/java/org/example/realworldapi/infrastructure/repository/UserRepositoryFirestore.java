package org.example.realworldapi.infrastructure.repository;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.SetOptions;
import lombok.SneakyThrows;
import org.example.realworldapi.domain.model.entity.User;
import org.example.realworldapi.domain.model.repository.UserRepository;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserRepositoryFirestore implements UserRepository {

    private static final Logger LOGGER = Logger.getLogger(UserRepositoryFirestore.class);

    private Firestore firestore;

    public UserRepositoryFirestore(Firestore firestore) {
        this.firestore = firestore;
    }

    @Override
    @SneakyThrows
    public User create(User user) {
        firestore.collection("users").document(user.getId()).set(user).get();
        LOGGER.debug(String.format("Created new user with id %s", user.getId()));
        return user;
    }

    //TODO NDK: figure out if we need case-insensitive search Firestore

    @Override
    @SneakyThrows
    public Optional<User> findUserByEmail(String email) {
        Query query = firestore.collection("users").whereEqualTo("email", email);
        return query.get().get().toObjects(User.class).stream().findFirst();
    }

    @Override
    @SneakyThrows
    public boolean existsBy(String field, String value) {
        Query query = firestore.collection("users").whereEqualTo(field, value);
        return !query.get().get().isEmpty();
    }

    @Override
    @SneakyThrows
    public Optional<User> findUserById(String id) {
        return Optional.ofNullable(firestore.collection("users").document(id).get().get().toObject(User.class));
    }

    @Override
    @SneakyThrows
    public boolean existsUsername(String excludeId, String username) {
        Query query = firestore.collection("users").whereEqualTo("username", username);
        return query.get().get().toObjects(User.class).stream().anyMatch(user -> !excludeId.equals(user.getId()));
    }

    @Override
    @SneakyThrows
    public boolean existsEmail(String excludeId, String email) {
        Query query = firestore.collection("users").whereEqualTo("email", email);
        return query.get().get().toObjects(User.class).stream().anyMatch(user -> !user.getId().equals(excludeId));
    }

    @Override
    @SneakyThrows
    public Optional<User> findUserByUsername(String username) {
        Query query = firestore.collection("users").whereEqualTo("username", username);
        return query.get().get().toObjects(User.class).stream().findFirst();
    }

    @Override
    @SneakyThrows
    public User mergeUpdateableFields(User user) {
        firestore.collection("users").document(user.getId()).set(user, SetOptions.mergeFields("image", "username", "bio", "email")).get();
        LOGGER.debug(String.format("Updated user with id %s", user.getId()));
        return user;
    }


    @Override
    @SneakyThrows
    public User mergeUpdateableFieldsIncludingPassword(User user) {
        firestore.collection("users").document(user.getId()).set(user, SetOptions.mergeFields("image", "username", "bio", "email", "password")).get();
        LOGGER.debug(String.format("Updated user with id %s", user.getId()));
        return user;
    }

    @Override
    @SneakyThrows
    public User overwriteUser(User user) {
        firestore.collection("users").document(user.getId()).set(user).get();
        LOGGER.debug(String.format("Updated user with id %s", user.getId()));
        return user;
    }
}
