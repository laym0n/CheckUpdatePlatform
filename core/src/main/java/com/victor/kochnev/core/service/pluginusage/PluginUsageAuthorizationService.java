package com.victor.kochnev.core.service.pluginusage;

import java.util.UUID;

public interface PluginUsageAuthorizationService {
    boolean verifyUserCanUsePlugin(UUID pluginId, UUID userId);
}
