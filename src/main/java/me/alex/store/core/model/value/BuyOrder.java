package me.alex.store.core.model.value;

import lombok.Value;

@Value
public class BuyOrder {
    ProductDetails productDetails;
    Integer quantity;
}
