package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.WebResourceRepository;
import com.victor.kochnev.dal.converter.EntityWebResourceMapper;
import com.victor.kochnev.dal.entity.WebResourceEntity;
import com.victor.kochnev.dal.repository.jpa.PluginEntityRepository;
import com.victor.kochnev.dal.repository.jpa.WebResourceEntityRepository;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.enums.ObserveStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class WebResourceRepositoryImpl implements WebResourceRepository {
    private final WebResourceEntityRepository webResourceEntityRepository;
    private final PluginEntityRepository pluginEntityRepository;
    private final EntityWebResourceMapper webResourceMapper;

    @Override
    public WebResource create(WebResource webResource) {
        UUID pluginId = webResource.getPlugin().getId();

        WebResourceEntity newWebResource = webResourceMapper.mapToEntity(webResource);
        newWebResource.setPlugin(pluginEntityRepository.findById(pluginId).get());
        WebResourceEntity savedWebResource = webResourceEntityRepository.save(newWebResource);
        return webResourceMapper.mapToDomain(savedWebResource);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WebResource> findByNameAndPluginId(String name, UUID pluginId) {
        return webResourceEntityRepository.findByNameAndPluginId(name, pluginId)
                .map(webResourceMapper::mapToDomain);
    }

    @Override
    public WebResource findById(UUID webResourcesId) {
        return webResourceEntityRepository.findById(webResourcesId)
                .map(webResourceMapper::mapToDomain)
                .orElseThrow(() -> ResourceNotFoundException.create(WebResource.class, webResourcesId.toString(), "id"));
    }

    @Override
    @Transactional
    public WebResource update(WebResource updatedWebResource) {
        UUID webResourceId = updatedWebResource.getId();
        WebResourceEntity webResourceEntity = getWebResourceEntity(webResourceId);
        webResourceEntity = webResourceMapper.update(webResourceEntity, updatedWebResource);
        WebResourceEntity savedEntity = webResourceEntityRepository.save(webResourceEntity);
        return webResourceMapper.mapToDomain(savedEntity);
    }

    @Override
    @Transactional
    public WebResource setStatus(ObserveStatus status, UUID webResourceId) {
        WebResourceEntity webResourceEntity = getWebResourceEntity(webResourceId);
        webResourceEntity.setStatus(status);
        return webResourceMapper.mapToDomain(webResourceEntity);
    }

    private WebResourceEntity getWebResourceEntity(UUID webResourceId) {
        return webResourceEntityRepository.findById(webResourceId)
                .orElseThrow(() -> ResourceNotFoundException.create(WebResource.class, webResourceId.toString(), "id"));
    }
}
