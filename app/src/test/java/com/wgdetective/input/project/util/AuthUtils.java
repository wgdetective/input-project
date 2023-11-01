package com.wgdetective.input.project.util;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgdetective.input.project.auth.dto.SignInRequest;
import com.wgdetective.input.project.auth.dto.SignUpRequest;
import com.wgdetective.input.project.auth.model.Role;
import com.wgdetective.input.project.repository.TokenRepository;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthUtils {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final String ACCESS_TOKEN = "access_token";

    public static final String REFRESH_TOKEN = "refresh_token";

    public static Map<String, String> signUpUser(final MockMvc client, String name, String email, String password, Role role)
            throws Exception {
        var request = new SignUpRequest(name, email, password, role);

        // when
        final var authJson = client.perform(post("/api/v1/auth/sign-up")
                .content(OBJECT_MAPPER.writeValueAsString(request))
                .contentType(APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return OBJECT_MAPPER.readValue(authJson, new TypeReference<>() {});
    }

    public static Map<String, String> signInUser(final MockMvc client, String email, String password) throws Exception {
        var request = new SignInRequest(email, password);

        // when
        final var authJson = client.perform(post("/api/v1/auth/sign-in")
                .content(OBJECT_MAPPER.writeValueAsString(request))
                .contentType(APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return OBJECT_MAPPER.readValue(authJson, new TypeReference<>() {});
    }

    public static void cleanTokens(final TokenRepository tokenRepository, final String email) {
        tokenRepository.deleteByUserEmail(email);

    }

}
