package com.victor.kochnev.application.configuration;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;

@Configuration
public class LocalDevApplicationConfiguration {

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher
            (ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }
}
