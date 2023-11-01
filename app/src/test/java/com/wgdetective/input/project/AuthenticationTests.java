package com.wgdetective.input.project;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgdetective.input.project.auth.dto.SignInRequest;
import com.wgdetective.input.project.auth.dto.SignUpRequest;
import com.wgdetective.input.project.auth.model.Role;
import com.wgdetective.input.project.repository.TokenRepository;
import com.wgdetective.input.project.util.AuthUtils;
import org.junit.jupiter.api.AfterEach;
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

import static com.wgdetective.input.project.util.AuthUtils.ACCESS_TOKEN;
import static com.wgdetective.input.project.util.AuthUtils.REFRESH_TOKEN;
import static org.hamcrest.Matchers.hasLength;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
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

    @Autowired
    private TokenRepository tokenRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    public void cleanUp() {
        AuthUtils.cleanTokens(tokenRepository, "billybob@email.com");
        AuthUtils.cleanTokens(tokenRepository, "billy2bob2@email.com");
    }

    @Test
    @Order(1)
    void A1_signUp_test() throws Exception {
        // given
        var request = new SignUpRequest("Billy Bob", "billybob@email.com", "bIlY01", Role.MEAL_READ);

        // when
        client.perform(post("/api/v1/auth/sign-up")
                .content(objectMapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token", hasLength(148)))
                .andExpect(jsonPath("refresh_token", hasLength(148)));

        // given
        var request2 = new SignUpRequest("Billy2 Bob2", "billy2bob2@email.com", "bIlY02", Role.MEAL_ALL);

        // when
        client.perform(post("/api/v1/auth/sign-up")
                .content(objectMapper.writeValueAsString(request2))
                .contentType(APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token", hasLength(151)))
                .andExpect(jsonPath("refresh_token", hasLength(151)));
    }

    @Test
    @Order(2)
    void A2_signInAsReader_test() throws Exception {
        // given
        var request = new SignInRequest("billybob@email.com", "bIlY01");

        // when
        final var authJson = client.perform(post("/api/v1/auth/sign-in")
                .content(objectMapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token", hasLength(148)))
                .andExpect(jsonPath("refresh_token", hasLength(148)))
                .andReturn().getResponse().getContentAsString();
        final var map = objectMapper.readValue(authJson, Map.class);
        final var accessToken = map.get(ACCESS_TOKEN);

        client.perform(get("/api/v1/auth-test")
                .header(AUTHORIZATION, "Bearer " + accessToken))
                // then
                .andExpect(status().isOk());
        client.perform(delete("/api/v1/auth-test")
                .header(AUTHORIZATION, "Bearer " + accessToken))
                // then
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(3)
    void A2_signInAsWriter_test() throws Exception {
        // given
        var request = new SignInRequest("billy2bob2@email.com", "bIlY02");

        // when
        final var authJson = client.perform(post("/api/v1/auth/sign-in")
                .content(objectMapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token", hasLength(151)))
                .andExpect(jsonPath("refresh_token", hasLength(151)))
                .andReturn().getResponse().getContentAsString();
        final var map = objectMapper.readValue(authJson, Map.class);
        final var accessToken = map.get(ACCESS_TOKEN);

        client.perform(get("/api/v1/auth-test")
                .header(AUTHORIZATION, "Bearer " + accessToken))
                // then
                .andExpect(status().isOk());
        client.perform(delete("/api/v1/auth-test")
                .header(AUTHORIZATION, "Bearer " + accessToken))
                // then
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    void A3_refreshToken_test() throws Exception {
        // given
        var request = new SignInRequest("billybob@email.com", "bIlY01");

        final var authJson = client.perform(post("/api/v1/auth/sign-in")
                .content(objectMapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token", hasLength(148)))
                .andExpect(jsonPath("refresh_token", hasLength(148)))
                .andReturn().getResponse().getContentAsString();
        final var map = objectMapper.readValue(authJson, Map.class);
        final var refreshToken = map.get(REFRESH_TOKEN);

        Thread.sleep(1000);

        // when
        client.perform(post("/api/v1/auth/refresh")
                .header(AUTHORIZATION, "Bearer " + refreshToken))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token", hasLength(148)))
                .andExpect(jsonPath("refresh_token", hasLength(148)))
                .andReturn().getResponse().getContentAsString();
    }

}
