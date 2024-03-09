package com.victor.kochnev.integration.plugin.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("app.integration.plugin")
@Data
public class PluginIntegrationProperties {
    private String basePath;
}
