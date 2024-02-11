package com.victor.kochnev.core.service.webresource;

import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.enums.WebResourceStatus;

import java.util.UUID;

public interface WebResourceService {
    WebResource updateOrCreate(UUID pluginId, WebResourcePluginDto webResourcePluginDto);

    void setStatus(WebResourceStatus status, UUID webResourcesId);
}
