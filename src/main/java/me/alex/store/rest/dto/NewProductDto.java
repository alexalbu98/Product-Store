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
public class NewProductDto {
    @NotNull
    protected Long storeId;
    @NotBlank
    protected String name;
    @NotBlank
    protected String description;
    @NotNull
    protected Integer availableStock;
    @NotNull
    protected Price price;
}
