package com.victor.kochnev.domain.entity.builder;

import com.victor.kochnev.domain.entity.WebResource;

import java.time.ZonedDateTime;
import java.util.UUID;

public class WebResourceBuilder {
    public static final String DEFAULT_NAME = "name";
    public static final String DEFAULT_DESCRIPTION = "description";

    private WebResourceBuilder() {
    }

    public static WebResource.WebResourceBuilder<?, ?> defaultBuilder() {
        return WebResource.builder()
                .description(DEFAULT_DESCRIPTION)
                .name(DEFAULT_NAME);
    }

    public static WebResource.WebResourceBuilder<?, ?> persistedDefaultBuilder() {
        return defaultBuilder()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
