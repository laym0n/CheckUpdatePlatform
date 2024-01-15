package com.victor.kochnev.rest.presenters.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties("rest.presenters.jwt")
@Data
public class JwtConfigurationProperties {
    private final Duration duration;
    private final String secret;
}
