package com.victor.kochnev.core.repository;

import com.victor.kochnev.core.dto.dal.GetPluginUsagesDalRequestDto;
import com.victor.kochnev.core.dto.dal.GetPluginUsagesDalResponseDto;
import com.victor.kochnev.domain.entity.PluginUsage;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PluginUsageRepository {
    List<PluginUsage> findPluginUsageWithExpiredDateAfterOrNull(UUID userId, UUID pluginId, ZonedDateTime expiredDate);

    PluginUsage create(PluginUsage pluginUsage);

    GetPluginUsagesDalResponseDto getByFilters(GetPluginUsagesDalRequestDto dalRequestDto);

    Optional<PluginUsage> findLastByExpiredDate(UUID userId, UUID pluginId);

    Optional<PluginUsage> findAnyByUserIdAndPluginId(UUID userId, UUID pluginId);
}
