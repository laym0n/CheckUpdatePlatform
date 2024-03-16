package com.victor.kochnev.integration.plugin.client;

import com.victor.kochnev.core.dto.plugin.CanObserveResponseDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.integration.PluginClient;
import com.victor.kochnev.integration.plugin.api.dto.CanObserveRequest;
import com.victor.kochnev.integration.plugin.api.dto.WebResourceAddRequest;
import com.victor.kochnev.integration.plugin.api.dto.WebResourceRemoveRequest;
import com.victor.kochnev.integration.plugin.client.factory.WebResourceClientFactory;
import com.victor.kochnev.integration.plugin.converter.PluginResponseMapper;
import com.victor.kochnev.integration.plugin.exception.PluginIntegrationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

        log.info("Send request to plugin {} {}", baseUrl, request);
        CanObserveResponseDto responseDto;
        try {
            responseDto = webResourceClient.canObserve(request)
                    .map(pluginResponseMapper::mapToCore).block();
        } catch (Exception e) {
            String msg = ExceptionUtils.getMessage(e);
            log.error("Send can observe request to {} with error {}", baseUrl, msg);
            throw new PluginIntegrationException(msg, e);
        }
        log.info("Send request to plugin {} successful {}", baseUrl, responseDto);
        return responseDto;
    }

    @SneakyThrows
    @Override
    public WebResourcePluginDto addResourceForObserving(String baseUrl, String resourceDescription) {
        WebResourceClient webResourceClient = clientFactory.webResourceClient(baseUrl);
        var request = new WebResourceAddRequest();
        request.setDescription(resourceDescription);

        log.info("Send request to plugin {} {}", baseUrl, request);
        WebResourcePluginDto response;
        try {
            response = webResourceClient.add(request)
                    .map(pluginResponseMapper::mapToCore)
                    .block();
        } catch (Exception e) {
            String msg = ExceptionUtils.getMessage(e);
            log.error("Send addResourceForObserving request to {} with error {}", baseUrl, msg);
            throw new PluginIntegrationException(msg, e);
        }
        log.info("Send request to plugin {} successful {}", baseUrl, response);
        return response;
    }

    @SneakyThrows
    @Override
    public void removeResourceFromObserve(String baseUrl, String resourceName) {
        WebResourceClient webResourceClient = clientFactory.webResourceClient(baseUrl);
        var request = new WebResourceRemoveRequest();
        request.setName(resourceName);

        log.info("Send request to plugin {} {}", baseUrl, request);
        try {
            webResourceClient.remove(request).block();
        } catch (Exception e) {
            String msg = ExceptionUtils.getMessage(e);
            log.error("Send removeResourceFromObserve request to {} with error {}", baseUrl, msg);
            throw new PluginIntegrationException(msg, e);
        }
        log.info("Send request to plugin {} successful", baseUrl);
    }
}
