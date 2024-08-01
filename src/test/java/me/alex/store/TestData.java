package me.alex.store;

import me.alex.store.core.model.*;

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

    public static Store testStore() {
        return new Store(null,
                new StoreDescription("test store", "description",
                        new Address("test", "test", "test", "test")));
    }
}
