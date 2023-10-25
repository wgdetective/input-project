package com.wgdetective.input.project.repository.sql.mapper;

import com.wgdetective.input.project.auth.model.Token;
import com.wgdetective.input.project.repository.sql.entity.TokenEntity;
import org.mapstruct.Mapper;

/**
 * Mapper.
 */
@Mapper(componentModel = "spring")
public interface SqlTokenEntityMapper {

    Token map(final TokenEntity token);

    TokenEntity map(final Token token);

}
