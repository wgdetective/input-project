package com.wgdetective.input.project.controller.mapper;

import com.wgdetective.input.project.controller.dto.CreateNodeDto;
import com.wgdetective.input.project.controller.dto.NodeDto;
import com.wgdetective.input.project.model.CreateNode;
import com.wgdetective.input.project.model.Node;
import org.mapstruct.Mapper;

/**
 * Mapper.
 */
@Mapper(componentModel = "spring")
public interface NodeDtoMapper {

    NodeDto map(Node node);

    Node map(NodeDto node);

    CreateNode map(CreateNodeDto node);

}
