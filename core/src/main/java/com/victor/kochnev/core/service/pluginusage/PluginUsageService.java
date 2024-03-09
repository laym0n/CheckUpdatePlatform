package com.victor.kochnev.core.service.pluginusage;

import java.util.UUID;

public interface PluginUsageService {
    void verifyUserCanUsePlugin(UUID pluginId, UUID userId);
}
