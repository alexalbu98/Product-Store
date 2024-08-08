package me.alex.store.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.alex.store.core.model.value.ProductDetails;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto implements Serializable {
    @NotNull
    private Long productId;
    @Valid
    @NotNull
    private ProductDetails productDetails;
}
