package com.wgdetective.input.project.repository.sql.mapper;

import com.wgdetective.input.project.auth.model.User;
import com.wgdetective.input.project.repository.sql.entity.UserEntity;
import org.mapstruct.Mapper;

/**
 * Mapper.
 */
@Mapper(componentModel = "spring", uses = SqlTokenEntityMapper.class)
public interface SqlUserEntityMapper {

    User map(final UserEntity nodeEntity);

    UserEntity map(final User nodeEntity);

}
