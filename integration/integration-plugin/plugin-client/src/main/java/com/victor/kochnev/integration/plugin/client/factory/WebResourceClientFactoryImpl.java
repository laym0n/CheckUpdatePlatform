package com.victor.kochnev.integration.plugin.client.factory;

import com.victor.kochnev.integration.plugin.client.WebResourceClient;
import com.victor.kochnev.integration.plugin.client.invoker.ApiClient;
import org.springframework.stereotype.Component;

@Component
public class WebResourceClientFactoryImpl implements WebResourceClientFactory {
    @Override
    public WebResourceClient webResourceClient(String baseUrl) {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(baseUrl);
        return new WebResourceClient(apiClient);
    }
}
