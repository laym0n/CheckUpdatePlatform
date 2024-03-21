package com.victor.kochnev.core.service.pluginusage;

import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.core.repository.PluginUsageRepository;
import com.victor.kochnev.core.security.service.user.SecurityUserService;
import com.victor.kochnev.domain.entity.PluginUsage;
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
    private final PluginUsageRepository pluginUsageRepository;
    private final PluginRepository pluginRepository;

    @Override
    public boolean verifyUserCanUsePlugin(UUID pluginId, UUID userId) {
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
        UUID userId = securityUserService.getCurrentUser().getId();
        UUID ownerId = pluginRepository.getById(pluginId).getId();
        return ownerId.equals(userId);
    }
}
