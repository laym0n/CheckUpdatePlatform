package com.victor.kochnev.integration.telegram.config;

import com.victor.kochnev.integration.telegram.TelegramIntegrationScanMarker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = TelegramIntegrationScanMarker.class)
@EnableConfigurationProperties(TelegramConfigurationProperties.class)
public class TelegramIntegrationConfiguration {
}
