package com.victor.kochnev.core.service.pluginusage;

import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.PluginUsageRepository;
import com.victor.kochnev.domain.entity.PluginUsage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PluginUsageServiceImpl implements PluginUsageService {
    private final PluginUsageRepository pluginUsageRepository;

    @Override
    public PluginUsage findLastPluginUsageForUser(UUID pluginId, UUID userId) {
        Optional<PluginUsage> optionalPluginUsage = pluginUsageRepository.findLastPluginUsage(userId, pluginId);
        PluginUsage pluginUsage = optionalPluginUsage
                .orElseThrow(() -> ResourceNotFoundException.create(PluginUsage.class, userId.toString(), "userId"));
        return pluginUsage;
    }
}
