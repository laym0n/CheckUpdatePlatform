package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.PluginUsage;

import java.util.Optional;
import java.util.UUID;

public interface PluginUsageRepository {
    Optional<PluginUsage> findLastPluginUsage(UUID userId, UUID pluginId);
}
