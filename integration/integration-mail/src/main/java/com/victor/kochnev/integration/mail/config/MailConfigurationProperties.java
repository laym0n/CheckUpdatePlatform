package com.victor.kochnev.integration.mail.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.integration.mail")
@Data
public class MailConfigurationProperties {
    private String mailFrom;
}
