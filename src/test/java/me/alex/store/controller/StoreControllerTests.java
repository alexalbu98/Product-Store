package me.alex.store.controller;

import me.alex.store.AbstractPostgresTest;
import me.alex.store.core.model.UserRole;
import me.alex.store.core.model.value.Address;
import me.alex.store.core.model.value.Price;
import me.alex.store.core.model.value.ProductDetails;
import me.alex.store.core.model.value.StoreDetails;
import me.alex.store.core.repository.StoreRepository;
import me.alex.store.core.repository.UserRepository;
import me.alex.store.rest.dto.ExistingProductDto;
import me.alex.store.rest.dto.NewProductDto;
import me.alex.store.rest.dto.StoreDto;
import me.alex.store.rest.dto.UpdateProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static me.alex.store.TestData.testStoreOwnerUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class StoreControllerTests extends AbstractPostgresTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StoreRepository storeRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Owner can create a new store.")
    void create_store() throws Exception {
        String user = createUser();

        createStore(user, "test");

        var stores = storeRepository.findAll();
        assertEquals(1, stores.size());
    }

    @Test
    @DisplayName("Client receives forbidden when trying to create a store.")
    void client_fails_to_creates_store() throws Exception {
        var validStoreDetails = new StoreDetails("test", "test",
                new Address("test", "test", "test", "test"));
        var body = objectMapper.writeValueAsString(validStoreDetails);

        mvc.perform(post("/store")
                        .with(user("client").password("test").roles(UserRole.CLIENT.name()))
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Owner can see his stores.")
    void owner_can_see_stores() throws Exception {
        String owner = createUser();

        createStore(owner, "first");
        createStore(owner, "second");

        StoreDto[] storeDetails = getStoresOwnedByUser(owner);
        assertEquals(2, storeDetails.length);
    }

    @Test
    @DisplayName("Clients can see all the stores.")
    void show_all_stores() throws Exception {
        String owner = createUser();

        createStore(owner, "first");
        createStore(owner, "second");

        StoreDto[] storeDetails = getAllStores();
        assertEquals(2, storeDetails.length);
    }

    @Test
    @DisplayName("Store owner can add product.")
    void can_add_product() throws Exception {
        String owner = createUser();
        createStore(owner, "store");
        StoreDto[] storeDetails = getStoresOwnedByUser(owner);
        var store = storeDetails[0];

        var productDto = new NewProductDto(store.getId(),
                "product",
                "description",
                10,
                new Price(
                        10,
                        10,
                        "EURO"));
        addProduct(owner, productDto);

        var products = getAllProductsInStore(store.getId(), null);
        assertEquals(1, products.length);
        assertEquals(productDto.getName(), products[0].getName());
    }

    @Test
    @DisplayName("Store owner can update product.")
    void can_update() throws Exception {
        String owner = createUser();
        createStore(owner, "store");
        StoreDto[] storeDetails = getStoresOwnedByUser(owner);
        var store = storeDetails[0];

        var productDto = new NewProductDto(store.getId(),
                "product",
                "description",
                10,
                new Price(
                        10,
                        10,
                        "EURO"));
        addProduct(owner, productDto);
        var products = getAllProductsInStore(store.getId(), null);

        updateProduct(owner, store.getId(),
                new UpdateProductDto(products[0].getProductId(), new ProductDetails("test", null, null)));

        var updateProducts = getAllProductsInStore(store.getId(), null);
        assertEquals("test", updateProducts[0].getName());
    }

    @Test
    @DisplayName("Store owner can increase product stock.")
    void increase_product_stock() throws Exception {
        String owner = createUser();
        createStore(owner, "store");
        StoreDto[] storeDetails = getStoresOwnedByUser(owner);
        var store = storeDetails[0];

        var productDto = new NewProductDto(store.getId(),
                "product",
                "description",
                10,
                new Price(
                        10,
                        10,
                        "EURO"));
        addProduct(owner, productDto);
        var products = getAllProductsInStore(store.getId(), null);

        var initialStock = products[0].getAvailableStock();
        updateProductStock(owner, store.getId(), products[0].getProductId(), -1);

        var updateProducts = getAllProductsInStore(store.getId(), null);
        assertEquals(initialStock - 1, updateProducts[0].getAvailableStock());
    }

    @Test
    @DisplayName("Nobody besides owner cannot update product.")
    void cannot_update_owner() throws Exception {
        String owner = createUser();
        createStore(owner, "store");
        StoreDto[] storeDetails = getStoresOwnedByUser(owner);
        var store = storeDetails[0];

        var productDto = new NewProductDto(store.getId(),
                "product",
                "description",
                10,
                new Price(
                        10,
                        10,
                        "EURO"));
        addProduct(owner, productDto);
        var products = getAllProductsInStore(store.getId(), null);
        var newDetails = new UpdateProductDto(products[0].getProductId(), new ProductDetails("test", null, null));

        var body = objectMapper.writeValueAsString(newDetails);
        mvc.perform(put("/store/" + store.getId() + "/product")
                        .with(user("another owner").password("test").roles(UserRole.OWNER.name()))
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Clients can search a product by name")
    void search_product_by_name() throws Exception {
        String owner = createUser();
        createStore(owner, "store");
        StoreDto[] storeDetails = getStoresOwnedByUser(owner);
        var store = storeDetails[0];

        var firstProduct = new NewProductDto(store.getId(),
                "car",
                "description",
                10,
                new Price(
                        10,
                        10,
                        "EURO"));
        var secondProduct = new NewProductDto(store.getId(),
                "vacuum turbo",
                "description",
                10,
                new Price(
                        10,
                        10,
                        "EURO"));
        addProduct(owner, firstProduct);
        addProduct(owner, secondProduct);

        var products = getAllProductsInStore(store.getId(), "turbo");
        assertEquals(1, products.length);
        assertEquals(secondProduct.getName(), products[0].getName());
    }

    @Test
    @DisplayName("Add product with same name returns bad request.")
    void add_with_same_name_product() throws Exception {
        String owner = createUser();
        createStore(owner, "store");
        StoreDto[] storeDetails = getStoresOwnedByUser(owner);
        var store = storeDetails[0];
        var productDto = new NewProductDto(store.getId(),
                "product",
                "description",
                10,
                new Price(
                        10,
                        10,
                        "EURO"));

        addProduct(owner, productDto);

        String sameNameProduct = objectMapper.writeValueAsString(productDto);
        mvc.perform(post("/store/product")
                        .with(user(owner).password("test").roles(UserRole.OWNER.name()))
                        .content(sameNameProduct)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private String createUser() {
        var user = testStoreOwnerUser();
        userRepository.save(user);
        return user.getUsername();
    }

    private void createStore(String owner, String storeName) throws Exception {
        var validStoreDetails = new StoreDetails(storeName, "test",
                new Address("test", "test", "test", "test"));
        var body = objectMapper.writeValueAsString(validStoreDetails);

        mvc.perform(post("/store")
                        .with(user(owner).password("test").roles(UserRole.OWNER.name()))
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private void addProduct(String owner, NewProductDto newProductDto) throws Exception {
        var body = objectMapper.writeValueAsString(newProductDto);

        mvc.perform(post("/store/product")
                        .with(user(owner).password("test").roles(UserRole.OWNER.name()))
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private void updateProduct(String owner, Long storeId, UpdateProductDto updateProductDto) throws Exception {
        var body = objectMapper.writeValueAsString(updateProductDto);

        mvc.perform(put("/store/" + storeId + "/product")
                        .with(user(owner).password("test").roles(UserRole.OWNER.name()))
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private void updateProductStock(String owner, Long storeId, Long productId, int amount) throws Exception {
        mvc.perform(patch("/store/" + storeId + "/product/" + productId + "?amount=" + amount)
                        .with(user(owner).password("test").roles(UserRole.OWNER.name()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    private StoreDto[] getStoresOwnedByUser(String owner) throws Exception {
        MvcResult mvcResult = mvc.perform(get("/store")
                        .with(user(owner).password("test").roles(UserRole.OWNER.name())))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), StoreDto[].class);
    }

    private StoreDto[] getAllStores() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/store/all")
                        .with(user("client").password("test").roles(UserRole.CLIENT.name())))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), StoreDto[].class);
    }

    private ExistingProductDto[] getAllProductsInStore(Long storeId, String name) throws Exception {
        String url = name == null ? "/store/" + storeId + "/product"
                : "/store/" + storeId + "/product?name=" + name;
        MvcResult mvcResult = mvc.perform(get(url)
                        .with(user("client").password("test").roles(UserRole.CLIENT.name())))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExistingProductDto[].class);
    }

}
