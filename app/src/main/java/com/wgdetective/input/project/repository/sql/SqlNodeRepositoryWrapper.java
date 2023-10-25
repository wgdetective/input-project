package com.wgdetective.input.project.repository.sql;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import com.wgdetective.input.project.model.Node;
import com.wgdetective.input.project.repository.NodeRepository;
import com.wgdetective.input.project.repository.sql.mapper.SqlNodeEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Repository wrapper.
 */
@Repository
@RequiredArgsConstructor
public class SqlNodeRepositoryWrapper implements NodeRepository {

    private final SqlNodeRepository repository;

    private final SqlNodeEntityMapper mapper;

    @Override
    public Optional<Node> findById(final Long id) {
        return repository.findById(id).map(mapper::map);
    }

    @Override
    public List<Node> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).map(mapper::map).toList();
    }

    @Override
    public Node save(final Node node) {
        return mapper.map(repository.save(mapper.map(node)));
    }

    @Override
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

}
