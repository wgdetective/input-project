package com.wgdetective.input.project.repository.sql;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import com.wgdetective.input.project.auth.model.Token;
import com.wgdetective.input.project.repository.TokenRepository;
import com.wgdetective.input.project.repository.sql.mapper.SqlTokenEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SqlTokenRepositoryWrapper implements TokenRepository {

    private final SqlTokenRepository repository;

    private final SqlTokenEntityMapper mapper;

    private final SqlUserRepository userRepository;

    @Override
    public List<Token> findByUserIdAndExpiredAndRevoked(Long id) {
        return repository.findByUserIdAndExpiredAndRevoked(id, false, false).stream()
                .map(mapper::map).toList();
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return repository.findByToken(token).map(mapper::map);
    }

    @Override
    public Token save(final Token storedToken) {
        return mapper.map(repository.save(mapper.map(storedToken)));
    }

    @Override
    public List<Token> saveAll(final List<Token> tokens) {
        return StreamSupport.stream(
                repository.saveAll(tokens.stream().map(mapper::map).toList()).spliterator(),
                false)
                .map(mapper::map).toList();
    }

    @Override
    public void deleteByUserEmail(final String email) {
        userRepository.findByEmail(email).ifPresent(user -> repository.deleteByUserId(user.getId()));
    }

}
