package com.victor.kochnev.core.configuration;

import com.victor.kochnev.core.CoreBeanScanMarker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan(basePackageClasses = CoreBeanScanMarker.class)
public class CoreConfiguration {

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(4);
    }
}
