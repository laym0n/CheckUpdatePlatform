package com.victor.kochnev.core.service.pluginusage;

import com.victor.kochnev.domain.entity.PluginUsage;

import java.util.UUID;

public interface PluginUsageService {
    PluginUsage findLastPluginUsageForUser(UUID pluginId, UUID userId);
}
