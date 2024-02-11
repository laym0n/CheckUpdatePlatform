package com.victor.kochnev.core.service.webresourceobserving;

import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequest;
import com.victor.kochnev.domain.entity.WebResource;

/**
 * Сервис для управления сущностью {@link com.victor.kochnev.domain.entity.WebResource}
 */
public interface WebResourceObservingService {
    /**
     * Добавить ресурс для отслеживания
     *
     * @param webResource
     * @param request     запрос отслеживания ресурса
     * @return dto вебресурса
     */
    WebResourceObservingDto updateOrCreate(WebResource webResource, AddWebResourceForObservingRequest request);
}
