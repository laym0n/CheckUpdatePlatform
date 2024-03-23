package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.dal.converter.EntityPluginMapper;
import com.victor.kochnev.dal.entity.PluginEntity;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.repository.jpa.PluginEntityRepository;
import com.victor.kochnev.dal.repository.jpa.UserEntityRepository;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PluginRepositoryImpl implements PluginRepository {
    private final PluginEntityRepository pluginEntityRepository;
    private final EntityPluginMapper pluginMapper;
    private final UserEntityRepository userEntityRepository;

    @Override
    public Plugin getById(UUID id) {
        PluginEntity pluginEntity = getPluginEntityById(id);
        return pluginMapper.mapToDomain(pluginEntity);
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

    @Override
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

    private PluginEntity getPluginEntityById(UUID id) {
        return pluginEntityRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.create(Plugin.class, id.toString(), "id"));
    }
}
