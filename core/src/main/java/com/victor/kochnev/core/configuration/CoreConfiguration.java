package com.victor.kochnev.core.configuration;

import com.victor.kochnev.core.CoreBeanScanMarker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = CoreBeanScanMarker.class)
public class CoreConfiguration {
}
