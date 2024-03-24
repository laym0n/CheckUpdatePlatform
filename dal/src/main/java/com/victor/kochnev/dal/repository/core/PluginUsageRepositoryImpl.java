package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.repository.PluginUsageRepository;
import com.victor.kochnev.dal.converter.EntityPluginUsageMapper;
import com.victor.kochnev.dal.entity.PluginUsageEntity;
import com.victor.kochnev.dal.repository.jpa.PluginUsageEntityRepository;
import com.victor.kochnev.dal.spec.PluginUsageSpecification;
import com.victor.kochnev.domain.entity.PluginUsage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PluginUsageRepositoryImpl implements PluginUsageRepository {
    private final PluginUsageEntityRepository pluginUsageEntityRepository;
    private final EntityPluginUsageMapper pluginUsageMapper;

    @Override
    public List<PluginUsage> findPluginUsageWithExpiredDateAfterOrNull(UUID userId, UUID pluginId, ZonedDateTime expiredDate) {
        Specification<PluginUsageEntity> specification = PluginUsageSpecification.withUserIdPluginIdAndExpiredDateNullOrAfter(userId, pluginId, expiredDate);
        return pluginUsageEntityRepository.findAll(specification)
                .stream().map(pluginUsageMapper::mapToDomain).toList();
    }

    @Override
    @Transactional
    public PluginUsage create(PluginUsage pluginUsage) {
        PluginUsageEntity newPluginUsageEntity = pluginUsageMapper.mapToEntity(pluginUsage);
        newPluginUsageEntity = pluginUsageEntityRepository.save(newPluginUsageEntity);
        return pluginUsageMapper.mapToDomain(newPluginUsageEntity);
    }
}
