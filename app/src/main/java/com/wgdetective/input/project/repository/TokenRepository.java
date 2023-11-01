package com.wgdetective.input.project.repository;

import java.util.List;
import java.util.Optional;

import com.wgdetective.input.project.auth.model.Token;
import org.springframework.transaction.annotation.Transactional;

public interface TokenRepository {

    List<Token> findByUserIdAndExpiredAndRevoked(Long id);

    Optional<Token> findByToken(String token);

    Token save(Token storedToken);

    List<Token> saveAll(List<Token> tokens);

    @Transactional
    void deleteByUserEmail(String email);

}
