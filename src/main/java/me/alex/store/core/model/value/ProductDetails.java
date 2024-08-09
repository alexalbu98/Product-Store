package me.alex.store.core.model.value;

import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.relational.core.mapping.Embedded;

import java.io.Serializable;

import static org.springframework.data.relational.core.mapping.Embedded.OnEmpty.USE_EMPTY;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductDetails implements Serializable {
    private String name;
    private String description;
    @Valid
    @Embedded(onEmpty = USE_EMPTY, prefix = "price_")
    private Price price;
}
