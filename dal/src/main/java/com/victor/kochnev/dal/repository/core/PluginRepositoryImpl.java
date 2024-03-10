package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.dal.converter.EntityPluginMapper;
import com.victor.kochnev.dal.repository.jpa.PluginEntityRepository;
import com.victor.kochnev.domain.entity.Plugin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PluginRepositoryImpl implements PluginRepository {
    private final PluginEntityRepository pluginEntityRepository;
    private final EntityPluginMapper pluginMapper;

    @Override
    public Plugin getById(UUID id) {
        return pluginEntityRepository.findById(id)
                .map(pluginMapper::mapToDomain)
                .orElseThrow(() -> ResourceNotFoundException.create(Plugin.class, id.toString(), "id"));
    }

    @Override
    public Plugin findByWebResourceId(UUID webResourceId) {
        return pluginEntityRepository.findByWebResourceId(webResourceId)
                .map(pluginMapper::mapToDomain)
                .orElseThrow(() -> ResourceNotFoundException.create(Plugin.class, webResourceId.toString(), "webResourceId"));
    }

    @Override
    public Optional<Plugin> findByName(String accessToken) {
        return pluginEntityRepository.findByName(accessToken)
                .map(pluginMapper::mapToDomain);
    }
}
