package com.victor.kochnev.integration.plugin.client;

import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.integration.PluginClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// TODO: 07.02.2024 реализовать класс
@Component
@RequiredArgsConstructor
public class PluginClientImpl implements PluginClient {

    @Override
    public WebResourcePluginDto canObserve(String baseUrl, String resourceDescription) {
        return null;
    }

    @Override
    public WebResourcePluginDto addResourceForObserving(String baseUrl, String resourceDescription) {
        return null;
    }

    @Override
    public void removeResourceFromObserve(String resourceName) {

    }

    @Override
    public WebResourcePluginDto getResourceState(String resourceName) {
        return null;
    }
}
