package com.victor.kochnev.integration.telegram.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.integration.telegram")
@Data
public class TelegramConfigurationProperties {
    private String botUserName;
    private String botToken;
}
