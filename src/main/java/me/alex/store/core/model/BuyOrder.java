package me.alex.store.core.model;

import lombok.Value;

@Value
public class BuyOrder {
    ProductDetails productDetails;
    Integer quantity;
}
