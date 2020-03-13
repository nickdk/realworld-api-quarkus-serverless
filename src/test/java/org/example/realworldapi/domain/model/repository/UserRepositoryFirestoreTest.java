package org.example.realworldapi.domain.model.repository;

import io.quarkus.test.junit.QuarkusTest;
import org.example.realworldapi.DatabaseIntegrationTest;
import org.example.realworldapi.domain.model.entity.User;
import org.example.realworldapi.infrastructure.web.security.profile.Role;
import org.example.realworldapi.util.UserUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
public class UserRepositoryFirestoreTest extends DatabaseIntegrationTest {

    @Inject
    private UserRepository userRepository;

//  @BeforeEach
//  public void beforeEach() {
//    FirestoreOptions firestoreOptions =
//            FirestoreOptions.getDefaultInstance().toBuilder()
//                    .build();
//    firestore = firestoreOptions.getService();
//    userRepository = new UserRepositoryFirestore(firestore);
//  }

    @AfterEach
    public void afterEach() {
        clear();
    }

    @Test
    public void shouldCreateAnUser() {

        User user = UserUtils.create("user", "user@mail.com", "123");

        User result = userRepository.create(user);

//    transaction(() -> Assertions.assertNotNull(entityManager.find(User.class, result.getId())));
    }

//  @Test
//  public void shouldReturnAUserByEmail() {
//
//    User existingUser = createUser("user1", "user@mail.com", "123");
//
//    Optional<User> result =
//        transaction(() -> userRepository.findUserByEmail(existingUser.getEmail()));
//
//    Assertions.assertEquals(existingUser.getEmail(), result.orElse(new User()).getEmail());
//  }
//
//  @Test
//  public void givenAExistingEmail_shouldReturnTrue() {
//
//    User existingUser = createUser("user1", "user@mail.com", "123");
//
//    transaction(
//        () -> Assertions.assertTrue(userRepository.existsBy("email", existingUser.getEmail())));
//  }
//
//  @Test
//  public void givenAExistingUsername_shouldReturnTrue() {
//
//    User existingUser = createUser("user1", "user@mail.com", "123");
//
//    transaction(
//        () ->
//            Assertions.assertTrue(userRepository.existsBy("username", existingUser.getUsername())));
//  }
//
//  @Test
//  public void givenAInexistentEmail_shouldReturnFalse() {
//
//    String email = "user@mail.com";
//
//    transaction(() -> Assertions.assertFalse(userRepository.existsBy("email", email)));
//  }
//
//  @Test
//  public void givenAInexistentUsername_shouldReturnFalse() {
//
//    String username = "user1";
//
//    transaction(() -> Assertions.assertFalse(userRepository.existsBy("username", username)));
//  }
//
//  @Test
//  public void givenAnotherExistingUsername_shouldReturnTrue() {
//
//    User otherUser = createUser("user1", "user@mail.com", "123");
//
//    User currentUser = createUser("currentUser", "currentUser@mail.com", "123");
//
//    transaction(
//        () ->
//            Assertions.assertTrue(
//                userRepository.existsUsername(currentUser.getId(), otherUser.getUsername())));
//  }
//
//  @Test
//  public void givenInexistentUsername_shouldReturnFalse() {
//
//    User currentUser = createUser("currentUser", "currentUser@mail.com", "123");
//
//    transaction(
//        () ->
//            Assertions.assertFalse(
//                userRepository.existsUsername(currentUser.getId(), "superusername")));
//  }
//
//  @Test
//  public void shouldReturnFalseWhenUseCurrentUserUsername() {
//
//    User currentUser = createUser("currentUser", "currentUser@mail.com", "123");
//
//    transaction(
//        () ->
//            Assertions.assertFalse(
//                userRepository.existsUsername(currentUser.getId(), currentUser.getUsername())));
//  }
//
//  @Test
//  public void givenAnotherExistingEmail_shouldReturnTrue() {
//
//    User otherUser = createUser("user1", "user@mail.com", "123");
//
//    User currentUser = createUser("currentUser", "currentUser@mail.com", "123");
//
//    transaction(
//        () ->
//            Assertions.assertTrue(
//                userRepository.existsEmail(currentUser.getId(), otherUser.getEmail())));
//  }
//
//  @Test
//  public void givenInexistentEmail_shouldReturnFalse() {
//
//    User currentUser = createUser("currentUser", "currentUser@mail.com", "123");
//
//    transaction(
//        () ->
//            Assertions.assertFalse(
//                userRepository.existsEmail(currentUser.getId(), "user@mail.com")));
//  }
//
//  @Test
//  public void shouldReturnFalseWhenUseCurrentEmail() {
//
//    User currentUser = createUser("currentUser", "currentUser@mail.com", "123");
//
//    transaction(
//        () ->
//            Assertions.assertFalse(
//                userRepository.existsEmail(currentUser.getId(), currentUser.getEmail())));
//  }
//
//  @Test
//  public void givenValidUsername_shouldReturnUser() {
//
//    User user = createUser("user", "user@mail.com", "123");
//
//    transaction(
//        () ->
//            Assertions.assertTrue(
//                userRepository.findByUsernameOptional(user.getUsername()).isPresent()));
//  }

    private User createUser(String username, String email, String password, Role... role) {
        User user = UserUtils.create(username, email, password);
        firestore.collection("users").document(user.getId().toString()).set(user);
        return user;
    }
}
