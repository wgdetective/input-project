package com.wgdetective.input.project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.wgdetective.input.project.controller.dto.CreateNodeDto;
import com.wgdetective.input.project.repository.sql.SqlNodeRepository;
import com.wgdetective.input.project.repository.sql.entity.NodeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("local")
class NodeCRUDUseCaseTests extends AbstractAuthTests {

    @Autowired
    private SqlNodeRepository repository;

    @Test
    void saveNode() throws Exception {
        // given
        final var expectedJson = readResource("bdd/saveNode.json");
        var node = new CreateNodeDto(null, "Value");

        // when
        client.perform(post("/api/v1/node").content(objectMapper.writeValueAsString(node))
                .header(AUTHORIZATION, accessToken)
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
                .header(AUTHORIZATION, accessToken)
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
                .header(AUTHORIZATION, accessToken)
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
                .header(AUTHORIZATION, accessToken)
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
