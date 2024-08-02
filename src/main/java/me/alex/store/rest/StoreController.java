package me.alex.store.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.alex.store.core.model.value.StoreDetails;
import me.alex.store.core.service.StoreService;
import me.alex.store.rest.dto.ExistingProductDto;
import me.alex.store.rest.dto.NewProductDto;
import me.alex.store.rest.dto.StoreDto;
import me.alex.store.rest.dto.UpdateProductDto;
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
        storeService.openProductStore(principal.getName(), storeDetails);

        return "Store created successfully";
    }

    @GetMapping
    public List<StoreDto> findStoresOwnedByPrincipal(Principal principal) {
        return storeService.findUserStores(principal.getName());
    }

    @GetMapping("/all")
    public List<StoreDto> findAllStores() {
        return storeService.findAllStores();
    }

    @PostMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addProduct(Principal principal, @RequestBody @Valid NewProductDto newProductDto) {
        var product = productFactory.newPruductFromDto(newProductDto);
        storeService.addProductToStore(principal.getName(), product);

        return "Product add to store successfully.";
    }

    @GetMapping("{storeId}/product")
    public List<ExistingProductDto> findStoreProducts(@RequestParam(required = false) String name, @PathVariable Long storeId) {
        return storeService.findAllStoreProducts(storeId, name);
    }

    @PutMapping("{storeId}/product")
    public String updateProduct(Principal principal, @PathVariable Long storeId, @Valid @RequestBody UpdateProductDto updateProductDto) {
        storeService.updateProduct(principal.getName(), storeId, updateProductDto);

        return "Product update successfully!";
    }

    @PatchMapping("{storeId}/product/{productId}")
    public String changeProductStock(Principal principal, @PathVariable Long storeId,
                                     @PathVariable Long productId, @RequestParam Integer amount) {
        storeService.updateProductStock(principal.getName(), storeId, productId, amount);

        return "Product patched successfully!";
    }
}
