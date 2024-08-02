package me.alex.store.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.alex.store.core.model.value.Price;

@Getter
@NoArgsConstructor
public class ExistingProductDto extends NewProductDto {
    protected Long productId;

    public ExistingProductDto(Long productId, Long storeId, String name, String description, Integer availableStock, Price price) {
        super(storeId, name, description, availableStock, price);
        this.productId = productId;
    }

}
