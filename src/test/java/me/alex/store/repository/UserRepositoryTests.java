package me.alex.store.repository;

import me.alex.store.AbstractPostgresTest;
import me.alex.store.core.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static me.alex.store.TestData.testStoreOwnerUser;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTests extends AbstractPostgresTest {

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

        assertTrue(found.isPresent());
        assertEquals(user.getId(), found.get().getId());
        assertEquals(user.getUserRole(), found.get().getUserRole());
    }
}
