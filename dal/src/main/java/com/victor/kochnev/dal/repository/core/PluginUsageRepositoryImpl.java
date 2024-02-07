package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.repository.PluginUsageRepository;
import com.victor.kochnev.domain.entity.PluginUsage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PluginUsageRepositoryImpl implements PluginUsageRepository {
    @Override
    public Optional<PluginUsage> findLastPluginUsage(UUID userId, UUID pluginId) {
        return Optional.empty();
    }
}
