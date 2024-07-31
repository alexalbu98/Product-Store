package me.alex.store.model;

import lombok.Value;

@Value
public class ProductDetails {
    String name;
    String description;
    Price price;
}
