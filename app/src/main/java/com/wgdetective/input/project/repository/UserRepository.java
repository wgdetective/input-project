package com.wgdetective.input.project.repository;

import java.util.Optional;

import com.wgdetective.input.project.auth.model.User;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    User save(User user);

}
