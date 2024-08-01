package me.alex.store.controller;

import me.alex.store.AbstractPostgresTest;
import me.alex.store.core.repository.UserRepository;
import me.alex.store.rest.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class RegisterControllerTests extends AbstractPostgresTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void create_client() throws Exception {
        var dto = new UserDto("test", "test", "test", "test");
        var body = objectMapper.writeValueAsString(dto);

        mvc.perform(post("/register/client")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var users = userRepository.findAll();
        assertEquals(1, users.size());
    }
}
