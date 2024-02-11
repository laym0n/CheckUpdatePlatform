package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findUserByEmail(String email);

    User create(User user);

    List<User> findAllObserversOfWebResource(UUID pluginId, String webResourceName);

    User findById(UUID id);
}
