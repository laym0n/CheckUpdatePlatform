package com.victor.kochnev.api.dto;

import java.util.UUID;

public class WebResourceObservingAddRequestBodyBuilder {
    public static final String DEFAULT_DESCRIPTION = "description";
    public static final UUID DEFAULT_PLUGIN_ID = UUID.randomUUID();

    private WebResourceObservingAddRequestBodyBuilder() {
    }

    public static WebResourceObservingAddRequestBody defaultRequest() {
        var requestBody = new WebResourceObservingAddRequestBody();
        requestBody.setResourceDescription(DEFAULT_DESCRIPTION);
        requestBody.setPluginId(DEFAULT_PLUGIN_ID);
        return requestBody;
    }
}
