package com.victor.kochnev.api.dto;

import java.util.UUID;

public class WebResourceObservingAddRequestBuilder {
    public static final String DEFAULT_DESCRIPTION = "description";
    public static final UUID DEFAULT_PLUGIN_ID = UUID.randomUUID();

    private WebResourceObservingAddRequestBuilder() {
    }

    public static WebResourceObservingAddRequest defaultRequest() {
        var requestBody = new WebResourceObservingAddRequest();
        requestBody.setResourceDescription(DEFAULT_DESCRIPTION);
        requestBody.setPluginId(DEFAULT_PLUGIN_ID);
        return requestBody;
    }
}
