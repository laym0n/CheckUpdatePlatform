package com.victor.kochnev.domain.entity.builder;

import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.enums.ObserveStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

public class WebResourceDomainBuilder {
    public static final String DEFAULT_NAME = "name";
    public static final String DEFAULT_DESCRIPTION = "description";
    public static final ObserveStatus DEFAULT_STATUS = ObserveStatus.OBSERVE;

    private WebResourceDomainBuilder() {
    }

    public static WebResource.WebResourceBuilder<?, ?> defaultBuilder() {
        return WebResource.builder()
                .description(DEFAULT_DESCRIPTION)
                .name(DEFAULT_NAME)
                .status(DEFAULT_STATUS);
    }

    public static WebResource.WebResourceBuilder<?, ?> persistedDefaultBuilder() {
        return defaultBuilder()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
