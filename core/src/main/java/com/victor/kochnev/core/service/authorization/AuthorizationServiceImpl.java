package com.victor.kochnev.core.service.authorization;

import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.core.repository.PluginUsageRepository;
import com.victor.kochnev.core.repository.WebResourceObservingRepository;
import com.victor.kochnev.core.security.service.user.SecurityUserService;
import com.victor.kochnev.core.service.task.TaskService;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.entity.PluginUsage;
import com.victor.kochnev.domain.entity.Task;
import com.victor.kochnev.domain.enums.PluginStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component("authorizationService")
@RequiredArgsConstructor
@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {
    private final SecurityUserService securityUserService;
    private final TaskService taskService;
    private final PluginUsageRepository pluginUsageRepository;
    private final WebResourceObservingRepository observingRepository;
    private final PluginRepository pluginRepository;

    @Override
    public boolean verifyCurrentUserCanUsePlugin(UUID pluginId) {
        UUID userId = securityUserService.getCurrentUser().getId();
        List<PluginUsage> actualPluginUsageList = pluginUsageRepository.findPluginUsageWithExpiredDateAfterOrNull(userId, pluginId, ZonedDateTime.now());
        Optional<PluginUsage> optionalPluginUsage = actualPluginUsageList.stream().findFirst();
        if (optionalPluginUsage.isEmpty()) {
            return false;
        }
        PluginUsage pluginUsage = optionalPluginUsage.get();
        return pluginUsage.getPlugin().getStatus() != PluginStatus.CREATED;
    }

    @Override
    public boolean verifyAuthenticatedUserCanManagePlugin(UUID pluginId) {
        return verifyAuthenticatedUserCanManagePluginInternal(pluginRepository.getById(pluginId));
    }

    @Override
    public boolean verifyCurrentUserCanManageObserving(UUID observingId) {
        UUID currentUserId = securityUserService.getCurrentUser().getId();
        var observing = observingRepository.getById(observingId);
        if (!observing.getUser().getId().equals(currentUserId)) {
            return false;
        }
        return verifyCurrentUserCanUsePlugin(observing.getWebResource().getPlugin().getId());
    }

    @Override
    public boolean verifyAuthenticatedUserCanManageTask(UUID taskId) {
        Task task = taskService.getById(taskId);
        Plugin plugin = task.getPlugin();
        return verifyAuthenticatedUserCanManagePluginInternal(plugin);
    }

    @Override
    public boolean verifyAuthenticatedUserCanCreateOrUpdateFeedback(UUID pluginId) {
        UUID userId = securityUserService.getCurrentUser().getId();
        Optional<PluginUsage> anyPluginUsage = pluginUsageRepository.findAnyByUserIdAndPluginId(userId, pluginId);
        return anyPluginUsage.isPresent();
    }

    private boolean verifyAuthenticatedUserCanManagePluginInternal(Plugin plugin) {
        UUID userId = securityUserService.getCurrentUser().getId();
        UUID ownerId = plugin.getOwnerUser().getId();
        return ownerId.equals(userId);
    }
}
