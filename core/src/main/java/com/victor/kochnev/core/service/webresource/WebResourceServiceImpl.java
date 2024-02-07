package com.victor.kochnev.core.service.webresource;

import com.victor.kochnev.core.converter.DomainWebResourceMapper;
import com.victor.kochnev.core.dto.domain.WebResourceDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.dto.request.AddWebResourceRequest;
import com.victor.kochnev.core.exception.PluginUsageNotPermittedException;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.integration.PluginClient;
import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.core.repository.PluginUsageRepository;
import com.victor.kochnev.core.repository.WebResourceRepository;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.entity.PluginUsage;
import com.victor.kochnev.domain.entity.WebResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebResourceServiceImpl implements WebResourceService {
    private final DomainWebResourceMapper webResourceMapper;
    private final WebResourceRepository webResourceRepository;
    private final PluginUsageRepository pluginUsageRepository;
    private final PluginRepository pluginRepository;
    private final PluginClient pluginClient;

    @Override
    public WebResourceDto addWebResource(AddWebResourceRequest request) {
        Optional<PluginUsage> optionalPluginUsage = pluginUsageRepository.findLastPluginUsage(request.getUserId(), request.getPluginId());
        PluginUsage pluginUsage = optionalPluginUsage
                .orElseThrow(() -> ResourceNotFoundException.create(PluginUsage.class, request.getUserId().toString(), "userId"));
        if (!pluginUsage.canUse(ZonedDateTime.now())) {
            throw new PluginUsageNotPermittedException(request.getPluginId());
        }

        Plugin plugin = pluginRepository.findById(request.getPluginId());
        String baseUrl = plugin.getBaseUrl();
        WebResourcePluginDto webResourceDto = pluginClient.addResource(baseUrl, request.getResourceDescription());

        Optional<WebResource> optionalWebResource = webResourceRepository.findByNameAndPluginId(webResourceDto.getName(), request.getPluginId());
        WebResource webResource;
        if (optionalWebResource.isPresent()) {
            webResource = optionalWebResource.get();
            webResourceMapper.update(webResource, webResourceDto);
            webResourceRepository.update(webResource);
        } else {
            webResource = webResourceMapper.mapToEntity(webResourceDto);
            webResource = webResourceRepository.create(webResource);
        }
        return webResourceMapper.mapToDto(webResource);
    }
}
