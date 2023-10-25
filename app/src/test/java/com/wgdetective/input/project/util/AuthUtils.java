package com.wgdetective.input.project.util;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgdetective.input.project.auth.dto.AuthenticationRequest;
import com.wgdetective.input.project.auth.dto.RegisterRequest;
import com.wgdetective.input.project.auth.model.Role;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthUtils {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String registerUser(final MockMvc client, String email, String password, Role role) throws Exception {
        var request = new RegisterRequest("Billy", "Bob", email, password, role);

        // when
        final var authJson = client.perform(post("/api/v1/auth/register")
                .content(OBJECT_MAPPER.writeValueAsString(request))
                .contentType(APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        final var map = OBJECT_MAPPER.readValue(authJson, Map.class);
        return map.get("access_token").toString();
    }

    public static String loginUser(final MockMvc client, String email, String password) throws Exception {
        var request = new AuthenticationRequest(email, password);

        // when
        final var authJson = client.perform(post("/api/v1/auth/authenticate")
                .content(OBJECT_MAPPER.writeValueAsString(request))
                .contentType(APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        final var map = OBJECT_MAPPER.readValue(authJson, Map.class);
        return map.get("access_token").toString();
    }

}
