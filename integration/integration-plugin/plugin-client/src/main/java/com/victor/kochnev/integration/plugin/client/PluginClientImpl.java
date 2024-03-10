package com.victor.kochnev.integration.plugin.client;

import com.victor.kochnev.core.dto.plugin.CanObserveResponseDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.integration.PluginClient;
import com.victor.kochnev.integration.plugin.api.dto.CanObserveRequest;
import com.victor.kochnev.integration.plugin.api.dto.WebResourceAddRequest;
import com.victor.kochnev.integration.plugin.api.dto.WebResourceRemoveRequest;
import com.victor.kochnev.integration.plugin.client.factory.WebResourceClientFactory;
import com.victor.kochnev.integration.plugin.converter.PluginResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "app.integration.plugin.enabled", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class PluginClientImpl implements PluginClient {
    private final WebResourceClientFactory clientFactory;
    private final PluginResponseMapper pluginResponseMapper;

    @SneakyThrows
    @Override
    public CanObserveResponseDto canObserve(String baseUrl, String resourceDescription) {
        WebResourceClient webResourceClient = clientFactory.webResourceClient(baseUrl);
        var request = new CanObserveRequest();
        request.setDescription(resourceDescription);
        return webResourceClient.canObserve(request)
                .map(pluginResponseMapper::mapToCore).block();
    }

    @SneakyThrows
    @Override
    public WebResourcePluginDto addResourceForObserving(String baseUrl, String resourceDescription) {
        WebResourceClient webResourceClient = clientFactory.webResourceClient(baseUrl);
        var request = new WebResourceAddRequest();
        request.setDescription(resourceDescription);
        return webResourceClient.add(request)
                .map(pluginResponseMapper::mapToCore)
                .block();
    }

    @SneakyThrows
    @Override
    public void removeResourceFromObserve(String baseUrl, String resourceName) {
        WebResourceClient webResourceClient = clientFactory.webResourceClient(baseUrl);
        var request = new WebResourceRemoveRequest();
        request.setName(resourceName);
        webResourceClient.remove(request).block();
    }
}
