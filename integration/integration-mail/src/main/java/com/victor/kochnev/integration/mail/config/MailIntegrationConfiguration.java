package com.victor.kochnev.integration.mail.config;

import com.victor.kochnev.integration.mail.MailIntegrationScanMarker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = MailIntegrationScanMarker.class)
@EnableConfigurationProperties(MailConfigurationProperties.class)
public class MailIntegrationConfiguration {
}
