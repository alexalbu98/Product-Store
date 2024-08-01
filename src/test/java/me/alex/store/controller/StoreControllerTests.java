package me.alex.store.controller;

import me.alex.store.AbstractPostgresTest;
import me.alex.store.core.model.UserRole;
import me.alex.store.core.model.value.Address;
import me.alex.store.core.model.value.StoreDetails;
import me.alex.store.core.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static me.alex.store.TestData.testStoreOwnerUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void create_store() throws Exception {
        String user = createUser();

        var validStoreDetails = new StoreDetails("test", "test",
                new Address("test", "test", "test", "test"));
        var body = objectMapper.writeValueAsString(validStoreDetails);

        mvc.perform(post("/store")
                        .with(user(user).password("test").roles(UserRole.OWNER.name()))
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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

    private String createUser() {
        var user = testStoreOwnerUser();
        userRepository.save(user);
        return user.getUsername();
    }
}
