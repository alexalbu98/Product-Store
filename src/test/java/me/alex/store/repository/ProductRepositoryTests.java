package me.alex.store.repository;

import me.alex.store.AbstractPostgresTest;
import me.alex.store.core.repository.ProductRepository;
import me.alex.store.core.repository.StoreRepository;
import me.alex.store.core.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static me.alex.store.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ProductRepositoryTests extends AbstractPostgresTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Insert new product in database.")
    void insert_new_product() {
        var storeId = insertNewStore();
        var product = testProduct();
        product.setStoreRef(storeId);

        var saved = productRepository.save(product);

        assertAll(
                () -> assertNotNull(saved.getId()),
                () -> assertNotNull(saved.getVersion())
        );
    }

    @Test
    @DisplayName("Can retrieve inserted product.")
    void find_inserted() {
        var storeId = insertNewStore();
        var product = testProduct();
        product.setStoreRef(storeId);

        var saved = productRepository.save(product);
        var found = productRepository.findById(saved.getId());

        assertAll(
                () -> assertTrue(found.isPresent()),
                () -> assertEquals(found.get().getId(), saved.getId())
        );
    }

    @Test
    @DisplayName("Exists by name.")
    void exists_by_name() {
        var storeId = insertNewStore();
        var product = testProduct();
        product.setStoreRef(storeId);

        productRepository.save(product);
        assertTrue(productRepository.existsByNameInStore(product.getProductDetails().getName(), storeId));
        assertFalse(productRepository.existsByNameInStore(UUID.randomUUID().toString(), storeId));
    }

    @Test
    @DisplayName("On update version is incremented.")
    void version_is_incremented() {
        var storeId = insertNewStore();
        var product = testProduct();
        product.setStoreRef(storeId);

        var savedProduct = productRepository.save(product);
        savedProduct.increaseStock(10);
        var updated = productRepository.save(savedProduct);

        assertEquals(1, updated.getVersion());
    }

    private Long insertNewStore() {
        Long userId = userRepository.save(testStoreOwnerUser()).getId();
        return storeRepository.save(testStore(userId)).getId();
    }
}
