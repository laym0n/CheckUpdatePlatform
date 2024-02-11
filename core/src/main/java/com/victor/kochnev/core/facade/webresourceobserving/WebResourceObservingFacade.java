package com.victor.kochnev.core.facade.webresourceobserving;

import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequest;

public interface WebResourceObservingFacade {
    WebResourceObservingDto addWebResourceForObserving(AddWebResourceForObservingRequest request);
}
