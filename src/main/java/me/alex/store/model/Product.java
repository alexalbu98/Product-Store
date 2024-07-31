package me.alex.store.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Setter(AccessLevel.NONE)
    protected Long id;
    protected Long storeId;
    protected Long version;
    protected ProductDetails productDetails;
    protected Integer availableStock;
    protected Long unitsSold;

    public ProductDetails buy(Integer quantity) {
        if (availableStock - quantity < 0) {
            throw new IllegalStateException("Stock is too low for this purchase.");
        }

        version += 1;
        availableStock -= quantity;
        unitsSold += quantity;

        return productDetails;
    }
}
