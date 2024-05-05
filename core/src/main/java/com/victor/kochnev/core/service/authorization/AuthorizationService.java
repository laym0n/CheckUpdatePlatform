package com.victor.kochnev.core.service.authorization;

import java.util.UUID;

public interface AuthorizationService {
    boolean verifyCurrentUserCanUsePlugin(UUID pluginId);

    boolean verifyAuthenticatedUserCanManagePlugin(UUID pluginId);

    boolean verifyCurrentUserCanManageObserving(UUID observingId);

    boolean verifyAuthenticatedUserCanManageTask(UUID taskId);

    boolean verifyAuthenticatedUserCanCreateOrUpdateFeedback(UUID pluginId);
}
