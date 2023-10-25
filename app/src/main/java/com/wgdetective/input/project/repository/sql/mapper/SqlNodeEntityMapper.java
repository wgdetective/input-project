package com.wgdetective.input.project.repository.sql.mapper;

import com.wgdetective.input.project.model.Node;
import com.wgdetective.input.project.repository.sql.entity.NodeEntity;
import org.mapstruct.Mapper;

/**
 * Mapper.
 */
@Mapper(componentModel = "spring")
public interface SqlNodeEntityMapper {

    Node map(final NodeEntity nodeEntity);

    NodeEntity map(final Node nodeEntity);

}
