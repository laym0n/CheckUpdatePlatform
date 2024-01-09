package com.victor.kochnev.rest.presenters.configuration;

import com.victor.kochnev.rest.presenters.RestPresentersScanMarker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = RestPresentersScanMarker.class)
public class RestPresentersConfiguration {
}
