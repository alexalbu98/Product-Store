package me.alex.store.core.model.value;

import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.relational.core.mapping.Embedded;

import java.io.Serializable;

import static org.springframework.data.relational.core.mapping.Embedded.OnEmpty.USE_EMPTY;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductDetails implements Serializable {
    String name;
    String description;
    @Valid
    @Embedded(onEmpty = USE_EMPTY, prefix = "price_")
    Price price;
}
