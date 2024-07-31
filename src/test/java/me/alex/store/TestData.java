package me.alex.store;

import me.alex.store.model.Price;
import me.alex.store.model.Product;
import me.alex.store.model.ProductDetails;

import java.util.UUID;

public class TestData {
    public static Product randomProduct() {
        return new Product(null,
                null,
                1L,
                new ProductDetails(
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        new Price(10, 10, "EURO")
                ),
                10,
                0L
        );
    }
}
