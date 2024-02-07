package com.victor.kochnev.core.service.webresource;

import com.victor.kochnev.core.dto.domain.WebResourceDto;
import com.victor.kochnev.core.dto.request.AddWebResourceRequest;

/**
 * Сервис для управления сущностью {@link com.victor.kochnev.domain.entity.WebResource}
 */
public interface WebResourceService {
    /**
     * Добавить ресурс для отслеживания
     *
     * @param request запрос отслеживания ресурса
     * @return dto вебресурса
     */
    WebResourceDto addWebResource(AddWebResourceRequest request);
}
