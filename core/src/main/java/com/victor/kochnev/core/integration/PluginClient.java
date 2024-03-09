package com.victor.kochnev.core.integration;

import com.victor.kochnev.core.dto.plugin.CanObserveResponseDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;

public interface PluginClient {

    CanObserveResponseDto canObserve(String baseUrl, String resourceDescription);

    WebResourcePluginDto addResourceForObserving(String baseUrl, String resourceDescription);

    void removeResourceFromObserve(String baseUrl, String resourceName);
}
