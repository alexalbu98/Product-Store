package me.alex.store.core.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.alex.store.core.model.AppUser;
import me.alex.store.core.model.Product;
import me.alex.store.core.model.Store;
import me.alex.store.core.model.value.Price;
import me.alex.store.core.model.value.StoreDetails;
import me.alex.store.core.repository.ProductRepository;
import me.alex.store.core.repository.StoreRepository;
import me.alex.store.core.repository.UserRepository;
import me.alex.store.rest.dto.ExistingProductDto;
import me.alex.store.rest.dto.StoreDto;
import me.alex.store.rest.dto.UpdateProductDto;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

  private final StoreRepository storeRepository;
  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  private static final String USERNAME_KEY = "#username";
  private static final String CAFFEINE_CACHE_MANAGER = "caffeineCacheManager";
  private static final String REDIS_CACHE_MANAGER = "redisCacheManager";
  private static final String STORE_CACHE = "storeCache";
  private static final String PRODUCT_CACHE = "productCache";
  private static final String PRODUCT_STORE_REF = "#product.storeRef";
  private static final String STORE_ID = "#storeId";

  @Caching(evict = {
      @CacheEvict(cacheNames = STORE_CACHE,
          cacheManager = CAFFEINE_CACHE_MANAGER, key = USERNAME_KEY),
      @CacheEvict(cacheNames = STORE_CACHE,
          cacheManager = REDIS_CACHE_MANAGER, key = USERNAME_KEY)
  })
  public void openProductStore(String username, StoreDetails storeDetails) {
    var user = findUser(username);

    if (storeRepository.existsByName(storeDetails.getName())) {
      throw new IllegalStateException("Store name already exists.");
    }

    var newStore = new Store(user, storeDetails);
    storeRepository.save(newStore);
  }

  @Caching(cacheable = {
      @Cacheable(cacheNames = STORE_CACHE,
          cacheManager = CAFFEINE_CACHE_MANAGER, key = USERNAME_KEY),
      @Cacheable(cacheNames = STORE_CACHE,
          cacheManager = REDIS_CACHE_MANAGER, key = USERNAME_KEY)}
  )
  public StoreDto[] findUserStores(String username) {
    var user = findUser(username);

    return storeRepository.findAllByUserRef(user.getId())
        .stream()
        .map(this::convertToStoreDto)
        .toList()
        .toArray(new StoreDto[0]);
  }

  @Caching(evict = {
      @CacheEvict(cacheNames = PRODUCT_CACHE,
          cacheManager = CAFFEINE_CACHE_MANAGER, key = PRODUCT_STORE_REF),
      @CacheEvict(cacheNames = PRODUCT_CACHE,
          cacheManager = REDIS_CACHE_MANAGER, key = PRODUCT_STORE_REF),
  })
  public void addProductToStore(String username, Product product) {
    var user = findUser(username);

    if (!storeRepository.existsById(product.getStoreRef())) {
      throw new IllegalStateException(
          "The store with id: " + product.getStoreRef() + " does not exist.");
    }

    if (userCannotEditStore(user.getId(), product.getStoreRef())) {
      throw new IllegalStateException("User cannot modify this store.");
    }

    if (productRepository.existsByNameInStore(product.getProductDetails().getName(),
        product.getStoreRef())) {
      throw new IllegalStateException("Product name already exists in this store");
    }

    productRepository.save(product);
  }

  @Caching(cacheable = {
      @Cacheable(cacheNames = STORE_CACHE,
          cacheManager = CAFFEINE_CACHE_MANAGER),
      @Cacheable(cacheNames = STORE_CACHE,
          cacheManager = REDIS_CACHE_MANAGER)}
  )
  public List<StoreDto> findAllStores() {
    return storeRepository.findAll()
        .stream()
        .map(this::convertToStoreDto)
        .toList();
  }

  @Caching(cacheable = {
      @Cacheable(cacheNames = PRODUCT_CACHE,
          cacheManager = CAFFEINE_CACHE_MANAGER, key = STORE_ID),
      @Cacheable(cacheNames = PRODUCT_CACHE,
          cacheManager = REDIS_CACHE_MANAGER, key = STORE_ID)}
  )
  public List<ExistingProductDto> findAllStoreProducts(Long storeId, String name) {
    if (Strings.isBlank(name)) {
      return productRepository.findAllByStoreRef(storeId)
          .stream().map(this::convertToProductDto).toList();
    }

    return productRepository.findAllByStoreRefAndName(storeId, name)
        .stream().map(this::convertToProductDto).toList();
  }

  @Caching(evict = {
      @CacheEvict(cacheNames = PRODUCT_CACHE,
          cacheManager = CAFFEINE_CACHE_MANAGER, key = STORE_ID),
      @CacheEvict(cacheNames = PRODUCT_CACHE,
          cacheManager = REDIS_CACHE_MANAGER, key = STORE_ID)
  })
  public void updateProduct(String username, Long storeId, UpdateProductDto updateProductDto) {
    var user = findUser(username);

    if (!storeRepository.existsById(storeId)) {
      throw new IllegalStateException("The state with id: " + storeId + " does not exist");
    }

    if (userCannotEditStore(user.getId(), storeId)) {
      throw new IllegalStateException("User cannot edit the store.");
    }

    boolean productNameExists = updateProductDto.getProductDetails().getName() != null
        && productRepository.existsByNameInStore(updateProductDto.getProductDetails().getName(),
        storeId);
    if (productNameExists) {
      throw new IllegalStateException("Cannot update, the name already exists");
    }

    var product = findProduct(updateProductDto.getProductId());
    product.updateDetails(updateProductDto.getProductDetails());
    productRepository.save(product);
  }

  @Caching(evict = {
      @CacheEvict(cacheNames = PRODUCT_CACHE,
          cacheManager = CAFFEINE_CACHE_MANAGER, key = STORE_ID),
      @CacheEvict(cacheNames = PRODUCT_CACHE,
          cacheManager = REDIS_CACHE_MANAGER, key = STORE_ID)
  })
  public void updateProductStock(String username, Long storeId, Long productId, Integer amount) {
    var user = findUser(username);

    if (!storeRepository.existsById(storeId)) {
      throw new IllegalStateException("The state with id: " + storeId + " does not exist");
    }

    if (userCannotEditStore(user.getId(), storeId)) {
      throw new IllegalStateException("User cannot edit the store.");
    }

    var product = findProduct(productId);
    product.increaseStock(amount);
    productRepository.save(product);
  }

  private Product findProduct(Long productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> new IllegalStateException("The product does not exist."));
  }

  private boolean userCannotEditStore(Long userId, Long storeId) {
    return storeRepository.findAllByUserRef(userId)
        .stream().map(Store::getId)
        .noneMatch(id -> id.equals(storeId));
  }

  private AppUser findUser(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalStateException("Username does not exist."));
  }

  private StoreDto convertToStoreDto(Store store) {
    return new StoreDto(store.getId(),
        store.getStoreDetails().getName(),
        store.getStoreDetails().getDescription(),
        store.getStoreDetails().getAddress().toString());
  }

  private ExistingProductDto convertToProductDto(Product product) {
    return new ExistingProductDto(product.getId(),
        product.getStoreRef(),
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
