package me.alex.store.core.service;

import lombok.RequiredArgsConstructor;
import me.alex.store.core.model.Product;
import me.alex.store.core.model.Store;
import me.alex.store.core.model.User;
import me.alex.store.core.model.value.Price;
import me.alex.store.core.model.value.StoreDetails;
import me.alex.store.core.repository.ProductRepository;
import me.alex.store.core.repository.StoreRepository;
import me.alex.store.core.repository.UserRepository;
import me.alex.store.rest.dto.ProductDto;
import me.alex.store.rest.dto.StoreDto;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void openProductStore(String username, StoreDetails storeDetails) {
        var user = findUser(username);

        if (storeRepository.existsByName(storeDetails.getName())) {
            throw new IllegalStateException("Store name already exists.");
        }

        storeRepository.save(new Store(null, null, user.getId(), storeDetails));
    }

    public List<StoreDto> findUserStores(String username) {
        var user = findUser(username);

        return storeRepository.findAllByUserRef(user.getId())
                .stream()
                .map(this::convertToStoreDto)
                .toList();
    }

    public void addProductToStore(String username, Product product) {
        var user = findUser(username);

        if (!storeRepository.existsById(product.getStoreRef())) {
            throw new IllegalStateException("The store with id: " + product.getStoreRef() + " does not exist.");
        }

        if (!userCanEditStore(user.getId(), product.getStoreRef())) {
            throw new IllegalStateException("User cannot modify this store.");
        }

        if (productRepository.existsByNameInStore(product.getProductDetails().getName(), product.getStoreRef())) {
            throw new IllegalStateException("Product name already exists in this store");
        }

        productRepository.save(product);
    }

    public List<StoreDto> findAllStores() {
        return storeRepository.findAll()
                .stream()
                .map(this::convertToStoreDto)
                .toList();
    }

    public List<ProductDto> findAllStoreProducts(Long storeId, String name) {
        if (Strings.isBlank(name)) {
            return productRepository.findAllByStoreRef(storeId)
                    .stream().map(this::convertToProductDto).toList();
        }

        return productRepository.findAllByStoreRefAndName(storeId, name)
                .stream().map(this::convertToProductDto).toList();
    }

    private boolean userCanEditStore(Long userId, Long storeId) {
        return storeRepository.findAllByUserRef(userId)
                .stream().map(Store::getId)
                .anyMatch(id -> id.equals(storeId));
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Username does not exist."));
    }

    private StoreDto convertToStoreDto(Store store) {
        return new StoreDto(store.getId(),
                store.getStoreDetails().getName(),
                store.getStoreDetails().getDescription(),
                store.getStoreDetails().getAddress().toString());
    }

    private ProductDto convertToProductDto(Product product) {
        return new ProductDto(product.getStoreRef(),
                product.getProductDetails().getName(),
                product.getProductDetails().getDescription(),
                product.getAvailableStock(),
                new Price(
                        product.getProductDetails().getPrice().getUnit(),
                        product.getProductDetails().getPrice().getSubUnit(),
                        product.getProductDetails().getPrice().getCurrency()

                ));
    }
}
