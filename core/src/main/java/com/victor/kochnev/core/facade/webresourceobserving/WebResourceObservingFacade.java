package com.victor.kochnev.core.facade.webresourceobserving;

import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequest;
import com.victor.kochnev.core.dto.request.StopWebResourceObservingRequest;

public interface WebResourceObservingFacade {
    WebResourceObservingDto addWebResourceForObserving(AddWebResourceForObservingRequest request);

    WebResourceObservingDto stopWebResourceObserving(StopWebResourceObservingRequest request);
}
