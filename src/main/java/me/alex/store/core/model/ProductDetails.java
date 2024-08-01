package me.alex.store.core.model;

import lombok.Value;
import org.springframework.data.relational.core.mapping.Embedded;

import static org.springframework.data.relational.core.mapping.Embedded.OnEmpty.USE_EMPTY;

@Value
public class ProductDetails {
    String name;
    String description;
    @Embedded(onEmpty = USE_EMPTY, prefix = "price_")
    Price price;
}
