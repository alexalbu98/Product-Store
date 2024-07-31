package me.alex.store.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static me.alex.store.TestData.randomProduct;
import static org.junit.jupiter.api.Assertions.*;

class ProductTests {

    @Test
    @DisplayName("When a product is bought all the necessary data is updated.")
    void testProductBuy() {
        var product = randomProduct();
        var buyQuantity = 3;
        product.buy(buyQuantity);

        assertAll(
                () -> assertEquals(product.getVersion(), randomProduct().getVersion() + 1),
                () -> assertEquals(product.getAvailableStock(), randomProduct().getAvailableStock() - buyQuantity),
                () -> assertEquals(product.getUnitsSold(), randomProduct().getUnitsSold() + buyQuantity)
        );
    }

    @Test
    @DisplayName("Buying an exceeding quantity throws an exception")
    void buyExceedingQuantity() {
        var product = randomProduct();
        var buyQuantity = randomProduct().getAvailableStock() + 1;

        assertThrows(IllegalStateException.class, () -> {
            product.buy(buyQuantity);
        });
    }
}
