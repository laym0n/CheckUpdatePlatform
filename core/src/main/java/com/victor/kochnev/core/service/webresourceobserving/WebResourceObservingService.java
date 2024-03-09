package com.victor.kochnev.core.service.webresourceobserving;

import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequest;
import com.victor.kochnev.domain.entity.WebResourceObserving;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для управления сущностью {@link com.victor.kochnev.domain.entity.WebResourceObserving}
 */
public interface WebResourceObservingService {
    WebResourceObservingDto addObservingCascade(WebResourcePluginDto webResource, AddWebResourceForObservingRequest request);

    List<WebResourceObserving> findAllActualObservers(String name);

    WebResourceObserving getById(UUID observingId);

    boolean stopObservingCascade(UUID observingId);
}
