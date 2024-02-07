package com.victor.kochnev.core.integration;

import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;

public interface PluginClient {

    WebResourcePluginDto addResource(String baseUrl, String resourceDescription);

    void removeResource(String resourceName);

    WebResourcePluginDto getResourceState(String resourceName);
}
