package com.wgdetective.input.project.auth.service;

import java.util.Optional;

import com.wgdetective.input.project.auth.model.Token;
import com.wgdetective.input.project.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository repository;

    public Optional<Token> findByToken(final String jwt) {
        return repository.findByToken(jwt);
    }

}
