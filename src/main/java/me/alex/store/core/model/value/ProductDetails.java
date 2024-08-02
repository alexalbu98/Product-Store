package me.alex.store.core.model.value;

import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.relational.core.mapping.Embedded;

import static org.springframework.data.relational.core.mapping.Embedded.OnEmpty.USE_EMPTY;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductDetails {
    String name;
    String description;
    @Valid
    @Embedded(onEmpty = USE_EMPTY, prefix = "price_")
    Price price;
}
