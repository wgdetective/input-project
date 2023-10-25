package com.wgdetective.input.project;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgdetective.input.project.auth.dto.AuthenticationRequest;
import com.wgdetective.input.project.auth.dto.RegisterRequest;
import com.wgdetective.input.project.auth.model.Role;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasLength;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("local")
@TestMethodOrder(OrderAnnotation.class)
class AuthenticationTests {

    @Autowired
    private MockMvc client;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(1)
    void register() throws Exception {
        // given
        var request = new RegisterRequest("Billy", "Bob", "billybob@email.com", "bIlY01", Role.READER);

        // when
        client.perform(post("/api/v1/auth/register")
                .content(objectMapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token", hasLength(148)))
                .andExpect(jsonPath("refresh_token", hasLength(148)));

        // given
        var request2 = new RegisterRequest("Billy2", "Bob2", "billy2bob2@email.com", "bIlY02", Role.CREATOR);

        // when
        client.perform(post("/api/v1/auth/register")
                .content(objectMapper.writeValueAsString(request2))
                .contentType(APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token", hasLength(151)))
                .andExpect(jsonPath("refresh_token", hasLength(151)));
        Thread.sleep(1000);
    }

    @Test
    @Order(2)
    void loginAsReader() throws Exception {
        // given
        var request = new AuthenticationRequest("billybob@email.com", "bIlY01");

        // when
        final var authJson = client.perform(post("/api/v1/auth/authenticate")
                .content(objectMapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token", hasLength(148)))
                .andExpect(jsonPath("refresh_token", hasLength(148)))
                .andReturn().getResponse().getContentAsString();
        final var map = objectMapper.readValue(authJson, Map.class);
        final var accessToken = map.get("access_token");

        client.perform(get("/api/v1/auth-test")
                .header("Authorization", "Bearer " + accessToken))
                // then
                .andExpect(status().isOk());
        client.perform(delete("/api/v1/auth-test")
                .header("Authorization", "Bearer " + accessToken))
                // then
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(3)
    void loginAsCreator() throws Exception {
        // given
        var request = new AuthenticationRequest("billy2bob2@email.com", "bIlY02");

        // when
        final var authJson = client.perform(post("/api/v1/auth/authenticate")
                .content(objectMapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token", hasLength(151)))
                .andExpect(jsonPath("refresh_token", hasLength(151)))
                .andReturn().getResponse().getContentAsString();
        final var map = objectMapper.readValue(authJson, Map.class);
        final var accessToken = map.get("access_token");

        client.perform(get("/api/v1/auth-test")
                .header("Authorization", "Bearer " + accessToken))
                // then
                .andExpect(status().isOk());
        client.perform(delete("/api/v1/auth-test")
                .header("Authorization", "Bearer " + accessToken))
                // then
                .andExpect(status().isOk());
    }

}
