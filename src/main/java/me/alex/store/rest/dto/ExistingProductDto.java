package me.alex.store.rest.dto;

import lombok.*;
import me.alex.store.core.model.value.Price;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class ExistingProductDto extends NewProductDto {
    protected Long productId;

    public ExistingProductDto(Long productId, Long storeId, String name, String description, Integer availableStock, Price price) {
        super(storeId, name, description, availableStock, price);
        this.productId = productId;
    }

}
