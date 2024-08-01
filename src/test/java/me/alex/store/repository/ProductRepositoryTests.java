package me.alex.store.repository;

import me.alex.store.AbstractPostgresTest;
import me.alex.store.core.repository.ProductRepository;
import me.alex.store.core.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static me.alex.store.TestData.testProduct;
import static me.alex.store.TestData.testStore;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ProductRepositoryTests extends AbstractPostgresTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StoreRepository storeRepository;

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

    private Long insertNewStore() {
        return storeRepository.save(testStore()).getId();
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
}
