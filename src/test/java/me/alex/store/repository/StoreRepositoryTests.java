package me.alex.store.repository;

import me.alex.store.AbstractPostgresTest;
import me.alex.store.core.model.value.StoreDetails;
import me.alex.store.core.repository.StoreRepository;
import me.alex.store.security.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static me.alex.store.TestData.testStore;
import static me.alex.store.TestData.testStoreOwnerUser;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StoreRepositoryTests extends AbstractPostgresTest {

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
