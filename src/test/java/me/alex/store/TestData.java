package me.alex.store;

import me.alex.store.core.model.Price;
import me.alex.store.core.model.Product;
import me.alex.store.core.model.ProductDetails;

public class TestData {
    public static Product testProduct() {
        return new Product(null,
                null,
                1L,
                new ProductDetails(
                        "Test product",
                        "Test description",
                        new Price(10, 10, "EURO")
                ),
                10,
                0L
        );
    }
}
