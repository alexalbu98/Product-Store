package me.alex.store.model;

import me.alex.store.core.model.BuyOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static me.alex.store.TestData.testProduct;
import static org.junit.jupiter.api.Assertions.*;

class ProductTests {

    @Test
    @DisplayName("When a product is bought all the necessary data is updated.")
    void buyProduct() {
        var product = testProduct();
        var buyQuantity = 3;

        BuyOrder buyOrder = product.buy(buyQuantity);

        assertAll(
                () -> assertEquals(buyOrder.getQuantity(), buyQuantity),
                () -> assertEquals(buyOrder.getProductDetails(), testProduct().getProductDetails()),
                () -> assertEquals(product.getAvailableStock(), testProduct().getAvailableStock() - buyQuantity),
                () -> assertEquals(product.getUnitsSold(), testProduct().getUnitsSold() + buyQuantity)
        );
    }

    @Test
    @DisplayName("Buying an exceeding quantity throws an exception")
    void buyExceedingQuantity() {
        var product = testProduct();
        var buyQuantity = testProduct().getAvailableStock() + 1;

        assertThrows(IllegalStateException.class, () -> {
            product.buy(buyQuantity);
        });
    }

    @Test
    @DisplayName("Increase stock updates product version")
    void increaseStock() {
        var product = testProduct();
        var increaseByAmount = 4;

        product.increaseStock(increaseByAmount);

        assertAll(
                () -> assertEquals(product.getAvailableStock(), testProduct().getAvailableStock() + increaseByAmount)
        );
    }
}
