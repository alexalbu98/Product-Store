package me.alex.store.rest.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.alex.store.core.model.value.Price;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @NotNull
    private Long storeId;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Integer availableStock;
    @NotNull
    private Price price;
}
