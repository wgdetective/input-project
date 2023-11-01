package com.wgdetective.input.project;

import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgdetective.input.project.auth.model.Role;
import com.wgdetective.input.project.repository.TokenRepository;
import com.wgdetective.input.project.util.AuthUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public abstract class AbstractAuthTests {

    @Autowired
    protected MockMvc client;

    @Autowired
    protected TokenRepository tokenRepository;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected String accessToken;

    protected String refreshToken;

    protected String userEmail;

    @BeforeEach
    public void init() throws Exception {
        final var randomName = UUID.randomUUID().toString();
        userEmail = "@test.com";
        final var map = AuthUtils.signUpUser(client, randomName, randomName + userEmail, "12345",
                Role.MEAL_ALL);
        this.accessToken = "Bearer " + map.get(AuthUtils.ACCESS_TOKEN);
        this.refreshToken = "Bearer " + map.get(AuthUtils.REFRESH_TOKEN);
    }

    @AfterEach
    public void cleanUp() {
        AuthUtils.cleanTokens(tokenRepository, userEmail);
    }

}
