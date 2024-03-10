package com.victor.kochnev.integration.plugin.client;

import com.victor.kochnev.core.dto.plugin.CanObserveResponseDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.integration.PluginClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "app.integration.plugin.enabled", havingValue = "false", matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class StubPluginClientImpl implements PluginClient {

    @SneakyThrows
    @Override
    public CanObserveResponseDto canObserve(String baseUrl, String resourceDescription) {
        return null;
    }

    @SneakyThrows
    @Override
    public WebResourcePluginDto addResourceForObserving(String baseUrl, String resourceDescription) {
        return null;
    }

    @SneakyThrows
    @Override
    public void removeResourceFromObserve(String baseUrl, String resourceName) {
    }
}
