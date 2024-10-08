package me.alex.store.model;

import static me.alex.store.TestData.testProduct;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTests {

  @Test
  @DisplayName("When a product is bought all the necessary data is updated.")
  void buyProduct() {
    var product = testProduct();
    var buyQuantity = 3;

    product.buy(buyQuantity);

    assertAll(
        () -> assertEquals(product.getAvailableStock(),
            testProduct().getAvailableStock() - buyQuantity),
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
        () -> assertEquals(product.getAvailableStock(),
            testProduct().getAvailableStock() + increaseByAmount)
    );
  }
}
