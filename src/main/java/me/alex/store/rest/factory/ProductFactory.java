package me.alex.store.rest.factory;

import me.alex.store.core.model.Product;
import me.alex.store.core.model.value.Price;
import me.alex.store.core.model.value.ProductDetails;
import me.alex.store.rest.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory {

    public Product fromDto(ProductDto dto) {
        return new Product(null,
                null,
                dto.getStoreId(),
                new ProductDetails(dto.getName(),
                        dto.getDescription(),
                        new Price(dto.getPriceUnit(),
                                dto.getPriceSubUnit(),
                                dto.getPriceCurrency())),
                dto.getAvailableStock(),
                0L);
    }
}
