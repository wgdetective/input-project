package com.wgdetective.input.project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wgdetective.input.project.auth.model.Role;
import com.wgdetective.input.project.controller.dto.CreateNodeDto;
import com.wgdetective.input.project.repository.sql.SqlNodeRepository;
import com.wgdetective.input.project.repository.sql.entity.NodeEntity;
import com.wgdetective.input.project.util.AuthUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("local")
class NodeCRUDUseCaseTests {

    @Autowired
    private MockMvc client;

    @Autowired
    private SqlNodeRepository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String token;

    @BeforeEach
    public void init() throws Exception {
        this.token = "Bearer " + AuthUtils.registerUser(client, UUID.randomUUID().toString(), "12345", Role.CREATOR);
    }

    @Test
    void saveNode() throws Exception {
        // given
        final var expectedJson = readResource("bdd/saveNode.json");
        var node = new CreateNodeDto(null, "Value");

        // when
        client.perform(post("/api/v1/node").content(objectMapper.writeValueAsString(node))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        // clean up
        final var entity = repository.findAll().iterator().next();
        assert entity != null;
        repository.delete(entity);
    }

    @Test
    void updateNode() throws Exception {
        // given
        final var expectedJson = readResource("bdd/updateNode.json");
        var node = new NodeEntity();
        node.setValue("Value");
        repository.save(node);

        node.setValue("Value2");
        // when
        client.perform(put("/api/v1/node").content(objectMapper.writeValueAsString(node))
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        // clean up
        final var entity = repository.findAll().iterator().next();
        assert entity != null;
        repository.delete(entity);
    }

    @Test
    void testGetNodeById() throws Exception {
        // given
        final var expectedJson = readResource("bdd/getNodeById.json");
        var node = new NodeEntity();
        node.setValue("Value");
        node = repository.save(node);

        // when
        client.perform(get("/api/v1/node/" + node.getId())
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        // clean up
        repository.delete(node);
    }

    @Test
    void testFindAllNodes() throws Exception {
        // given
        final var expectedJson = readResource("bdd/findAllNodes.json");
        var node1 = new NodeEntity();
        node1.setValue("Value1");
        node1 = repository.save(node1);
        var node2 = new NodeEntity();
        node2.setValue("Value2");
        node2 = repository.save(node2);

        // when
        client.perform(get("/api/v1/node")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        // clean up
        repository.delete(node1);
        repository.delete(node2);
    }

    private String readResource(final String resourceName) throws IOException {
        try (final var resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(resourceName)) {
            return new String(Objects.requireNonNull(resourceAsStream).readAllBytes(), StandardCharsets.UTF_8);
        }
    }

}
