package com.victor.kochnev.core.dto.request;

import java.util.UUID;

public class AddWebResourceForObservingRequestBuilder {
    public static final String DEFAULT_RESOURCE_DESCRIPTION = "description";
    public static final UUID DEFAULT_USER_ID = UUID.randomUUID();
    public static final UUID DEFAULT_PLUGIN_ID = UUID.randomUUID();

    private AddWebResourceForObservingRequestBuilder() {
    }

    public static AddWebResourceForObservingRequest.AddWebResourceForObservingRequestBuilder defaultBuilder() {
        return AddWebResourceForObservingRequest.builder()
                .pluginId(DEFAULT_PLUGIN_ID)
                .userId(DEFAULT_USER_ID)
                .resourceDescription(DEFAULT_RESOURCE_DESCRIPTION);
    }
}
