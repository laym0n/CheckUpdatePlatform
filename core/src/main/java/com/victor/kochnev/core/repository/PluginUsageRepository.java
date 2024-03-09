package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.PluginUsage;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface PluginUsageRepository {
    List<PluginUsage> findPluginUsageWithExpiredDateAfterOrNull(UUID userId, UUID pluginId, ZonedDateTime expiredDate);
}
