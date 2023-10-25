package com.wgdetective.input.project.repository;

import java.util.List;
import java.util.Optional;

import com.wgdetective.input.project.model.Node;

/**
 * NodeRepository interface.
 */
public interface NodeRepository {

    Optional<Node> findById(Long id);

    List<Node> findAll();

    Node save(Node node);

    void deleteById(Long id);

}
