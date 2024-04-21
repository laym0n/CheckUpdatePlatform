package com.victor.kochnev.core.facade.webresourceobserving;

import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequestDto;
import com.victor.kochnev.core.dto.request.GetWebResourceObservingsRequestDto;
import com.victor.kochnev.core.dto.request.StopWebResourceObservingRequestDto;
import com.victor.kochnev.core.dto.response.GetWebResouceObservingsResponseDto;

import java.util.UUID;

public interface WebResourceObservingFacade {
    WebResourceObservingDto addWebResourceForObserving(AddWebResourceForObservingRequestDto request);

    WebResourceObservingDto stopWebResourceObserving(StopWebResourceObservingRequestDto request);

    GetWebResouceObservingsResponseDto getByFilters(GetWebResourceObservingsRequestDto request);

    boolean checkAccess(UUID userId, UUID webResourceObservingId);
}
