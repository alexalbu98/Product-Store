package me.alex.store.controller;

import me.alex.store.AbstractPostgresTest;
import me.alex.store.core.model.UserRole;
import me.alex.store.core.model.value.Address;
import me.alex.store.core.model.value.StoreDetails;
import me.alex.store.core.repository.StoreRepository;
import me.alex.store.core.repository.UserRepository;
import me.alex.store.rest.dto.ProductDto;
import me.alex.store.rest.dto.StoreDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        var productDto = new ProductDto(store.getId(),
                "product",
                "description",
                10,
                10,
                10,
                "EURO");

        addProduct(owner, productDto);

        var products = getAllProductsInStore(store.getId());
        assertEquals(1, products.length);
    }

    @Test
    @DisplayName("Add product with same name returns bad request.")
    void same_name_product() throws Exception {
        String owner = createUser();
        createStore(owner, "store");
        StoreDto[] storeDetails = getStoresOwnedByUser(owner);
        var store = storeDetails[0];
        var productDto = new ProductDto(store.getId(),
                "product",
                "description",
                10,
                10,
                10,
                "EURO");

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

    private void addProduct(String owner, ProductDto productDto) throws Exception {
        var body = objectMapper.writeValueAsString(productDto);

        mvc.perform(post("/store/product")
                        .with(user(owner).password("test").roles(UserRole.OWNER.name()))
                        .content(body)
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

    private ProductDto[] getAllProductsInStore(Long storeId) throws Exception {
        MvcResult mvcResult = mvc.perform(get("/store/" + storeId + "/product")
                        .with(user("client").password("test").roles(UserRole.CLIENT.name())))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProductDto[].class);
    }

}
