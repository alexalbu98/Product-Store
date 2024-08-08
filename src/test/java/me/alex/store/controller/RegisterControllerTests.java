package me.alex.store.controller;

import me.alex.store.AbstractTest;
import me.alex.store.core.model.UserRole;
import me.alex.store.core.repository.UserRepository;
import me.alex.store.rest.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class RegisterControllerTests extends AbstractTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Create a new user with client role.")
    void create_client() throws Exception {
        var validDto = new UserDto("test", "test", "test", "test");
        var body = objectMapper.writeValueAsString(validDto);

        mvc.perform(post("/register/client")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var users = userRepository.findAll();
        assertEquals(1, users.size());
        assertEquals(UserRole.CLIENT, users.get(0).getUserRole());
        assertNotEquals(validDto.getPassword(), users.get(0).getPassword());
    }

    @Test
    @DisplayName("Create a new user with owner role.")
    void create_owner() throws Exception {
        var validDto = new UserDto("test", "test", "test", "test");
        var body = objectMapper.writeValueAsString(validDto);

        mvc.perform(post("/register/owner")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var users = userRepository.findAll();
        assertEquals(1, users.size());
        assertEquals(UserRole.OWNER, users.get(0).getUserRole());
        assertNotEquals(validDto.getPassword(), users.get(0).getPassword());
    }

    @Test
    @DisplayName("Invalid user data returns bad request.")
    void invalid_user() throws Exception {
        var invalidDto = new UserDto("", "test", "test", "test");
        var body = objectMapper.writeValueAsString(invalidDto);

        mvc.perform(post("/register/owner")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Create a user with existing username returns bad request")
    void same_username() throws Exception {
        var validDto = new UserDto("test", "test", "test", "test");
        var body = objectMapper.writeValueAsString(validDto);

        mvc.perform(post("/register/owner")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post("/register/client")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
