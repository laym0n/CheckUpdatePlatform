package com.victor.kochnev.core.service.webresource;

import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.enums.ObserveStatus;

import java.util.Optional;
import java.util.UUID;

public interface WebResourceService {
    WebResource updateOrCreate(UUID pluginId, WebResourcePluginDto webResourcePluginDto, ObserveStatus notObserve);

    WebResource setStatus(ObserveStatus status, UUID webResourceId);

    void update(UUID pluginId, WebResourcePluginDto updatedWebResourceDto);

    Optional<WebResource> findByNameAndPluginId(String name, UUID pluginId);
}
