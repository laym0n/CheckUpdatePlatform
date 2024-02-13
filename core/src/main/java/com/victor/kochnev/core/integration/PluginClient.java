package com.victor.kochnev.core.integration;

import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;

public interface PluginClient {

    WebResourcePluginDto canObserve(String baseUrl, String resourceDescription);

    WebResourcePluginDto addResourceForObserving(String baseUrl, String resourceDescription);

    void removeResourceFromObserve(String resourceName);

    WebResourcePluginDto getResourceState(String resourceName);
}
