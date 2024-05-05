package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.dto.dal.GetPluginUsagesDalRequestDto;
import com.victor.kochnev.core.dto.dal.GetPluginUsagesDalResponseDto;
import com.victor.kochnev.core.repository.PluginUsageRepository;
import com.victor.kochnev.dal.converter.EntityPluginUsageMapper;
import com.victor.kochnev.dal.entity.PluginUsageEntity;
import com.victor.kochnev.dal.repository.jpa.PluginUsageEntityRepository;
import com.victor.kochnev.dal.spec.PluginUsageSpecification;
import com.victor.kochnev.domain.entity.PluginUsage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
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

    @Override
    public GetPluginUsagesDalResponseDto getByFilters(GetPluginUsagesDalRequestDto dalRequestDto) {
        var usagesSpec = prepareSpecification(dalRequestDto);
        List<PluginUsage> pluginUsages = pluginUsageEntityRepository.findAll(usagesSpec).stream()
                .map(pluginUsageMapper::mapToDomain)
                .toList();

        var response = new GetPluginUsagesDalResponseDto();
        response.setPluginUsages(pluginUsages);
        return response;
    }

    @Override
    public Optional<PluginUsage> findLastByExpiredDate(UUID userId, UUID pluginId) {
        return pluginUsageEntityRepository.findLastByExpiredDate(userId, pluginId)
                .map(pluginUsageMapper::mapToDomain);
    }

    private Specification<PluginUsageEntity> prepareSpecification(GetPluginUsagesDalRequestDto requestDto) {
        Specification<PluginUsageEntity> spec = PluginUsageSpecification.getAll();
        if (requestDto == null || requestDto.getFilters() == null) {
            return spec;
        }
        var filters = requestDto.getFilters();
        if (ObjectUtils.isNotEmpty(filters.getPluginIds())) {
            spec = spec.and(PluginUsageSpecification.byPluginIds(filters.getPluginIds()));
        }
        if (ObjectUtils.isNotEmpty(filters.getUserIds())) {
            spec = spec.and(PluginUsageSpecification.byUserIds(filters.getUserIds()));
        }
        return spec;
    }
}
