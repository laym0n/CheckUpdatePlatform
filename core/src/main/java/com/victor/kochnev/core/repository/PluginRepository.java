package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.Plugin;

import java.util.Optional;
import java.util.UUID;

public interface PluginRepository {
    Plugin getById(UUID id);

    Plugin findByWebResourceId(UUID webResourceId);

    Optional<Plugin> findByName(String accessToken);

    Plugin create(Plugin newPlugin);
}
