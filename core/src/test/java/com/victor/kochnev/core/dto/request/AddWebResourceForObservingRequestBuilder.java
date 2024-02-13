package com.victor.kochnev.core.dto.request;

import com.victor.kochnev.domain.value.object.ObserveSettingsBuilder;

import java.util.Optional;
import java.util.UUID;

public class AddWebResourceForObservingRequestBuilder {
    public static final String DEFAULT_RESOURCE_DESCRIPTION = "description";

    private AddWebResourceForObservingRequestBuilder() {
    }

    public static AddWebResourceForObservingRequest.AddWebResourceForObservingRequestBuilder defaultBuilder() {
        return AddWebResourceForObservingRequest.builder()
                .pluginId(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .resourceDescription(DEFAULT_RESOURCE_DESCRIPTION)
                .observeSettings(Optional.of(ObserveSettingsBuilder.defaultObserveSettings()));
    }
}
