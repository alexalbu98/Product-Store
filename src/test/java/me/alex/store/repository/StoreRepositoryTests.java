package me.alex.store.repository;

import static me.alex.store.TestData.testStore;
import static me.alex.store.TestData.testStoreOwnerUser;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import me.alex.store.AbstractContainerTest;
import me.alex.store.core.model.value.StoreDetails;
import me.alex.store.core.repository.StoreRepository;
import me.alex.store.core.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class StoreRepositoryTests extends AbstractContainerTest {

  @Autowired
  StoreRepository storeRepository;

  @Autowired
  UserRepository userRepository;

  @Test
  @DisplayName("Create a new store and save it to database.")
  void insert_new_store() {
    var userId = userRepository.save(testStoreOwnerUser()).getId();
    var store = storeRepository.save(testStore(userId));

    assertAll(
        () -> assertNotNull(store.getId()),
        () -> assertNotNull(store.getVersion())
    );
  }

  @Test
  @DisplayName("Retrieve an inserted store.")
  void find_saved_store() {
    var userId = userRepository.save(testStoreOwnerUser()).getId();
    var inserted = storeRepository.save(testStore(userId));
    var found = storeRepository.findById(inserted.getId());

    assertAll(
        () -> assertTrue(found.isPresent()),
        () -> assertEquals(found.get().getId(), inserted.getId())
    );
  }

  @Test
  @DisplayName("Check name already exists for store.")
  void name_already_exists() {
    var userId = userRepository.save(testStoreOwnerUser()).getId();
    var inserted = storeRepository.save(testStore(userId));

    var exists = storeRepository.existsByName(inserted.getStoreDetails().getName());

    assertTrue(exists);
  }

  @Test
  @DisplayName("On update version is incremented.")
  void version_is_incremented() {
    var userId = userRepository.save(testStoreOwnerUser()).getId();
    var inserted = storeRepository.save(testStore(userId));
    inserted.setStoreDetails(new StoreDetails("new name", "new desc",
        inserted.getStoreDetails().getAddress()));

    var updated = storeRepository.save(inserted);

    assertEquals(1, updated.getVersion());
  }

}
