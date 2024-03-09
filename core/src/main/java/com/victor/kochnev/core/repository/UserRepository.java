package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findUserByEmail(String email);

    User create(User user);

    User getById(UUID id);
}
