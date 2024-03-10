package com.victor.kochnev.integration.plugin.configuration;

import com.victor.kochnev.integration.plugin.PluginIntegrationScanMarker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackageClasses = PluginIntegrationScanMarker.class)
@EnableConfigurationProperties(PluginIntegrationProperties.class)
@EnableWebMvc
public class PluginIntegrationConfiguration {
}
