package com.victor.kochnev.core.facade.webresourceobserving;

import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDomainDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequestDto;
import com.victor.kochnev.core.dto.request.StopWebResourceObservingRequestDto;

import java.util.UUID;

public interface WebResourceObservingFacade {
    WebResourceObservingDomainDto addWebResourceForObserving(AddWebResourceForObservingRequestDto request);

    WebResourceObservingDomainDto stopWebResourceObserving(StopWebResourceObservingRequestDto request);

    boolean checkAccess(UUID userId, UUID webResourceObservingId);
}
