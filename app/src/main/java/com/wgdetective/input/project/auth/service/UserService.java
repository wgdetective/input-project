package com.wgdetective.input.project.auth.service;

import java.util.Optional;

import com.wgdetective.input.project.auth.model.User;
import com.wgdetective.input.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Optional<User> findByUserName(final String username) {
        return repository.findByEmail(username);
    }

}
