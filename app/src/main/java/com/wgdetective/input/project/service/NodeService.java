package com.wgdetective.input.project.service;

import java.util.List;
import java.util.Optional;

import com.wgdetective.input.project.model.CreateNode;
import com.wgdetective.input.project.model.Node;
import com.wgdetective.input.project.repository.NodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NodeService {

    private final NodeRepository repository;

    public Optional<Node> getById(final Long id) {
        return repository.findById(id);
    }

    public List<Node> getAll() {
        return repository.findAll();
    }

    public Node save(final CreateNode node) {
        return repository.save(new Node(null, node.parentId(), node.value()));
    }

    public Node update(final Node node) {
        return repository.save(node);
    }

    public void delete(final Long id) {
        repository.deleteById(id);
    }

}
