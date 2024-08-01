package me.alex.store;

import me.alex.store.core.model.Product;
import me.alex.store.core.model.Store;
import me.alex.store.security.model.User;
import me.alex.store.security.model.UserRole;
import me.alex.store.core.model.value.Address;
import me.alex.store.core.model.value.Price;
import me.alex.store.core.model.value.ProductDetails;
import me.alex.store.core.model.value.StoreDetails;

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

    public static User testClientUser() {
        return new User(null,
                "test user",
                "test user",
                UserRole.CLIENT,
                "email",
                "phone"
        );
    }

    public static User testStoreOwnerUser() {
        return new User(null,
                "test user",
                "test user",
                UserRole.OWNER,
                "email",
                "phone"
        );
    }

    public static Store testStore(Long userRef) {
        return new Store(null,
                null,
                userRef,
                new StoreDetails("test store", "description",
                        new Address("test", "test", "test", "test")));
    }
}
