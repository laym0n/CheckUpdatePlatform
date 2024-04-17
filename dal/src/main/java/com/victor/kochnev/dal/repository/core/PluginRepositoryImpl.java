package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.dto.dal.GetPluginsDalRequestDto;
import com.victor.kochnev.core.dto.dal.GetPluginsDalResponseDto;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.dal.converter.EntityPluginMapper;
import com.victor.kochnev.dal.entity.PluginEntity;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.repository.jpa.PluginEntityRepository;
import com.victor.kochnev.dal.repository.jpa.UserEntityRepository;
import com.victor.kochnev.dal.spec.PluginSpecification;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PluginRepositoryImpl implements PluginRepository {
    private final PluginEntityRepository pluginEntityRepository;
    private final EntityPluginMapper pluginMapper;
    private final UserEntityRepository userEntityRepository;

    @Override
    @Transactional(readOnly = true)
    public Plugin getById(UUID id) {
        PluginEntity pluginEntity = getPluginEntityById(id);
        return pluginMapper.mapToDomain(pluginEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Plugin findByWebResourceId(UUID webResourceId) {
        return pluginEntityRepository.findByWebResourceId(webResourceId)
                .map(pluginMapper::mapToDomain)
                .orElseThrow(() -> ResourceNotFoundException.create(Plugin.class, webResourceId.toString(), "webResourceId"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Plugin> findByName(String accessToken) {
        return pluginEntityRepository.findByName(accessToken)
                .map(pluginMapper::mapToDomain);
    }

    @Override
    @Transactional
    public Plugin create(Plugin newPlugin) {
        UUID userId = newPlugin.getOwnerUser().getId();
        UserEntity userEntity = userEntityRepository.findById(userId)
                .orElseThrow(() -> ResourceNotFoundException.create(User.class, userId.toString(), "id"));
        PluginEntity pluginEntity = pluginMapper.mapToEntity(newPlugin);
        pluginEntity.setOwnerUser(userEntity);
        pluginEntity = pluginEntityRepository.save(pluginEntity);
        return pluginMapper.mapToDomain(pluginEntity);
    }

    @Override
    @Transactional
    public Plugin update(Plugin plugin) {
        PluginEntity dbPluginEntity = getPluginEntityById(plugin.getId());
        PluginEntity updatedPluginEntity = pluginMapper.mapToEntity(plugin);
        pluginMapper.update(dbPluginEntity, updatedPluginEntity);
        return pluginMapper.mapToDomain(dbPluginEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public GetPluginsDalResponseDto getByFilters(GetPluginsDalRequestDto request) {
        Specification<PluginEntity> spec = prepareSpecification(request);

        List<Plugin> pluginList = pluginEntityRepository.findAll(spec)
                .stream()
                .map(pluginMapper::mapToDomain)
                .toList();

        GetPluginsDalResponseDto response = new GetPluginsDalResponseDto();
        response.setPlugins(pluginList);
        return response;
    }

    private PluginEntity getPluginEntityById(UUID id) {
        return pluginEntityRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.create(Plugin.class, id.toString(), "id"));
    }

    private Specification<PluginEntity> prepareSpecification(GetPluginsDalRequestDto requestDto) {
        Specification<PluginEntity> spec = PluginSpecification.getAll();
        if (requestDto == null || requestDto.getFilters() == null) {
            return spec;
        }
        var filters = requestDto.getFilters();
        if (StringUtils.isNotEmpty(filters.getName())) {
            spec = spec.and(PluginSpecification.byName(filters.getName()));
        }
        if (ObjectUtils.isNotEmpty(filters.getTags())) {
            spec = spec.and(PluginSpecification.byTags(filters.getTags()));
        }
        return spec;
    }
}
