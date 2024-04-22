package com.victor.kochnev.core.service.webresourceobserving;

import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequestDto;
import com.victor.kochnev.core.dto.request.GetWebResourceObservingsRequestDto;
import com.victor.kochnev.core.dto.response.GetWebResouceObservingsResponseDto;
import com.victor.kochnev.domain.entity.WebResourceObserving;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для управления сущностью {@link com.victor.kochnev.domain.entity.WebResourceObserving}
 */
public interface WebResourceObservingService {
    WebResourceObservingDto addOrUpdateObservingCascade(WebResourcePluginDto webResource, AddWebResourceForObservingRequestDto request);

    List<WebResourceObserving> findAllActualObservings(String name);

    WebResourceObserving getById(UUID observingId);

    boolean stopObservingCascade(UUID observingId);

    GetWebResouceObservingsResponseDto getByFiltersForUser(GetWebResourceObservingsRequestDto request, UUID userId);

    WebResourceObserving continueObservingCascade(WebResourcePluginDto webResourcePluginDto, UUID observingId);
}
