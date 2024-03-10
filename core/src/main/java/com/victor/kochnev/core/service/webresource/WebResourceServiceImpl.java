package com.victor.kochnev.core.service.webresource;

import com.victor.kochnev.core.converter.DomainWebResourceMapper;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.core.repository.WebResourceObservingRepository;
import com.victor.kochnev.core.repository.WebResourceRepository;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.enums.ObserveStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebResourceServiceImpl implements WebResourceService {
    private final DomainWebResourceMapper domainWebResourceMapper;
    private final WebResourceRepository webResourceRepository;
    private final WebResourceObservingRepository webResourceObservingRepository;
    private final PluginRepository pluginRepository;

    @Override
    @Transactional
    public WebResource updateOrCreate(UUID pluginId, WebResourcePluginDto webResourcePluginDto, ObserveStatus status) {
        Optional<WebResource> webResourceOptional = findByNameAndPluginId(webResourcePluginDto.getName(), pluginId);
        WebResource webResource;
        if (webResourceOptional.isPresent()) {
            webResource = webResourceOptional.get();
            domainWebResourceMapper.update(webResource, webResourcePluginDto);
            webResource.setStatus(status);
            webResource = webResourceRepository.update(webResource);
        } else {
            webResource = domainWebResourceMapper.mapToEntity(webResourcePluginDto);
            webResource.setPlugin(pluginRepository.getById(pluginId));
            webResource.setStatus(status);
            webResource = webResourceRepository.create(webResource);
        }
        return webResource;
    }

    @Override
    @Transactional
    public WebResource setStatus(ObserveStatus status, UUID webResourceId) {
        return webResourceRepository.setStatus(status, webResourceId);
    }

    @Override
    @Transactional
    public void update(UUID pluginId, WebResourcePluginDto updatedWebResourceDto) {
        Optional<WebResource> optionalWebResource = findByNameAndPluginId(updatedWebResourceDto.getName(), pluginId);
        WebResource webResource = optionalWebResource
                .orElseThrow(() -> ResourceNotFoundException.create(WebResource.class, pluginId + " " + updatedWebResourceDto.getName(), "pluginId name"));
        domainWebResourceMapper.update(webResource, updatedWebResourceDto);
        webResourceRepository.update(webResource);
    }

    @Override
    @Transactional
    public Optional<WebResource> findByNameAndPluginId(String name, UUID pluginId) {
        return webResourceRepository.findByNameAndPluginId(name, pluginId);
    }
}
