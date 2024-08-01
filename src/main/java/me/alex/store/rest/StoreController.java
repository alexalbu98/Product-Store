package me.alex.store.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.alex.store.core.model.value.StoreDetails;
import me.alex.store.core.service.StoreService;
import me.alex.store.rest.dto.ProductDto;
import me.alex.store.rest.dto.StoreDto;
import me.alex.store.rest.factory.ProductFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/store")
public class StoreController {
    private final StoreService storeService;
    private final ProductFactory productFactory;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createStore(Principal principal, @RequestBody @Valid StoreDetails storeDetails) {
        storeService.createProductStore(principal.getName(), storeDetails);

        return "Store created successfully";
    }

    @GetMapping
    public List<StoreDto> findStoresOwnedByPrincipal(Principal principal) {
        return storeService.findStores(principal.getName());
    }

    @GetMapping("/all")
    public List<StoreDto> findAllStores() {
        return storeService.findAll();
    }

    @PostMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addProduct(Principal principal, @RequestBody @Valid ProductDto productDto) {
        var product = productFactory.fromDto(productDto);
        storeService.addProduct(principal.getName(), product);

        return "Product add to store successfully.";
    }

    @GetMapping("{storeId}/product")
    public List<ProductDto> findStoreProducts(@PathVariable Long storeId) {
        return storeService.findAllProducts(storeId);
    }
}
