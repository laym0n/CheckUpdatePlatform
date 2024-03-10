package com.victor.kochnev.rest.presenters.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties("app.rest.security.jwt")
@Data
public class JwtConfigurationProperties {
    /**
     * Длительность жизни Jwt токена
     */
    private Duration duration;
    /**
     * Секрет для генерации Jwt токена
     */
    private String secret;
}
