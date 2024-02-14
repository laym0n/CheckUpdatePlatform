package com.victor.kochnev.core.service.webresource;

import com.victor.kochnev.core.converter.DomainWebResourceMapper;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
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

    @Override
    @Transactional
    public WebResource updateOrCreate(UUID pluginId, WebResourcePluginDto webResourcePluginDto) {
        Optional<WebResource> webResourceOptional = webResourceRepository.findByNameAndPluginId(webResourcePluginDto.getName(), pluginId);
        WebResource webResource;
        if (webResourceOptional.isPresent()) {
            webResource = webResourceOptional.get();
            domainWebResourceMapper.update(webResource, webResourcePluginDto);
        } else {
            webResource = domainWebResourceMapper.mapToEntity(webResourcePluginDto);
            webResourceRepository.create(webResource);
        }
        return webResource;
    }

    @Override
    @Transactional
    public void setStatus(ObserveStatus status, UUID webResourceId) {
        WebResource webResource = webResourceRepository.findById(webResourceId);
        webResource.setStatus(status);
    }

    @Override
    @Transactional
    public boolean isNeedStopObserve(UUID webResourceId) {
        WebResource webResource = webResourceRepository.findById(webResourceId);
        if (ObserveStatus.NOT_OBSERVE == webResource.getStatus()) {
            return false;
        }
        int count = webResourceRepository.countObserversWithStatus(webResourceId, ObserveStatus.OBSERVE);
        return count == 0;
    }
}
