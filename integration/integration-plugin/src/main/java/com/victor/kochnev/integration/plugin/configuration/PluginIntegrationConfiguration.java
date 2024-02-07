package com.victor.kochnev.integration.plugin.configuration;

import com.victor.kochnev.integration.plugin.PluginIntegrationScanMarker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = PluginIntegrationScanMarker.class)
public class PluginIntegrationConfiguration {
}
