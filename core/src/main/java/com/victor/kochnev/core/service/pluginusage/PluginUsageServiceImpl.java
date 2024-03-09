package com.victor.kochnev.core.service.pluginusage;

import com.victor.kochnev.core.exception.PluginUsageNotPermittedException;
import com.victor.kochnev.core.repository.PluginUsageRepository;
import com.victor.kochnev.domain.entity.PluginUsage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PluginUsageServiceImpl implements PluginUsageService {
    private final PluginUsageRepository pluginUsageRepository;

    @Override
    public void verifyUserCanUsePlugin(UUID pluginId, UUID userId) {
        List<PluginUsage> actualPluginUsageList = pluginUsageRepository.findPluginUsageWithExpiredDateAfterOrNull(userId, pluginId, ZonedDateTime.now());
        if (actualPluginUsageList.isEmpty()) {
            throw new PluginUsageNotPermittedException(pluginId);
        }
    }
}
