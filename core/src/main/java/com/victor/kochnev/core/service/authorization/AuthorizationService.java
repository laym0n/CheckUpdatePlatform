package com.victor.kochnev.core.service.authorization;

import java.util.UUID;

public interface AuthorizationService {
    boolean verifyUserCanUsePlugin(UUID pluginId, UUID userId);

    boolean verifyAuthenticatedUserCanManagePlugin(UUID pluginId);
}
