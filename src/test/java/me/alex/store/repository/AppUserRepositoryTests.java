package me.alex.store.repository;

import static me.alex.store.TestData.testStoreOwnerUser;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import me.alex.store.AbstractContainerTest;
import me.alex.store.core.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AppUserRepositoryTests extends AbstractContainerTest {

  @Autowired
  UserRepository userRepository;

  @Test
  void insert_new_user() {
    var user = userRepository.save(testStoreOwnerUser());
    assertNotNull(user.getId());
  }

  @Test
  @DisplayName("Retrieve inserted user.")
  void find_user() {
    var user = userRepository.save(testStoreOwnerUser());
    var found = userRepository.findById(user.getId());

    assertAll(
        () -> assertTrue(found.isPresent()),
        () -> assertEquals(user.getId(), found.get().getId()),
        () -> assertEquals(user.getUserRole(), found.get().getUserRole())
    );
  }

  @Test
  @DisplayName("Retrieve inserted user by username.")
  void find_by_username() {
    var user = userRepository.save(testStoreOwnerUser());
    var found = userRepository.findByUsername(user.getUsername());

    assertAll(
        () -> assertTrue(found.isPresent()),
        () -> assertEquals(user.getId(), found.get().getId()),
        () -> assertEquals(user.getUserRole(), found.get().getUserRole())
    );
  }
}
