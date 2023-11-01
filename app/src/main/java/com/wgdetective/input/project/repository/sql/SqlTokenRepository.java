package com.wgdetective.input.project.repository.sql;

import java.util.List;
import java.util.Optional;

import com.wgdetective.input.project.repository.sql.entity.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlTokenRepository extends CrudRepository<TokenEntity, Long> {

    List<TokenEntity> findByUserIdAndExpiredAndRevoked(Long id, Boolean expired, Boolean revoked);

    Optional<TokenEntity> findByToken(String token);

    void deleteByUserId(Long userId);

}
