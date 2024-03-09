package com.victor.kochnev.integration.plugin.configuration;

import com.victor.kochnev.integration.plugin.PluginIntegrationScanMarker;
import com.victor.kochnev.integration.plugin.client.WebResourceClient;
import com.victor.kochnev.integration.plugin.client.invoker.ApiClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackageClasses = PluginIntegrationScanMarker.class)
@EnableConfigurationProperties(PluginIntegrationProperties.class)
@EnableWebMvc
public class PluginIntegrationConfiguration {

    @Bean
    public WebResourceClient pluginWebResourceClient(ApiClient apiClient) {
        return new WebResourceClient(apiClient);
    }

    @Bean
    public ApiClient pluginApiClient(PluginIntegrationProperties properties) {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(properties.getBasePath());
        return apiClient;
    }
}
