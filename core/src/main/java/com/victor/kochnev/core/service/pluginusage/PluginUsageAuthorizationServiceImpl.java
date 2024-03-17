package com.victor.kochnev.core.service.pluginusage;

import com.victor.kochnev.core.repository.PluginUsageRepository;
import com.victor.kochnev.domain.entity.PluginUsage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Component("pluginUsageAuthorizationService")
@RequiredArgsConstructor
@Slf4j
public class PluginUsageAuthorizationServiceImpl implements PluginUsageAuthorizationService {
    private final PluginUsageRepository pluginUsageRepository;

    @Override
    public boolean verifyUserCanUsePlugin(UUID pluginId, UUID userId) {
        List<PluginUsage> actualPluginUsageList = pluginUsageRepository.findPluginUsageWithExpiredDateAfterOrNull(userId, pluginId, ZonedDateTime.now());
        return !actualPluginUsageList.isEmpty();
    }
}