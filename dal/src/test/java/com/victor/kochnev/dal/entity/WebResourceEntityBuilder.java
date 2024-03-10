package com.victor.kochnev.dal.entity;

import com.victor.kochnev.domain.enums.ObserveStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

public class WebResourceEntityBuilder {
    public static final String DEFAULT_NAME = "name";
    public static final String DEFAULT_DESCRIPTION = "description";
    public static final ObserveStatus DEFAULT_STATUS = ObserveStatus.OBSERVE;

    private WebResourceEntityBuilder() {
    }

    public static WebResourceEntity.WebResourceEntityBuilder<?, ?> defaultBuilder() {
        return WebResourceEntity.builder()
                .description(DEFAULT_DESCRIPTION)
                .name(DEFAULT_NAME)
                .status(DEFAULT_STATUS)
                .plugin(PluginEntityBuilder.defaultBuilder().build());
    }

    public static WebResourceEntity.WebResourceEntityBuilder<?, ?> persistedDefaultBuilder() {
        return defaultBuilder()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }

    public static WebResourceEntity.WebResourceEntityBuilder<?, ?> postfixBuilder(int postfix) {
        return WebResourceEntity.builder()
                .description(DEFAULT_DESCRIPTION)
                .name(DEFAULT_NAME + postfix)
                .status(DEFAULT_STATUS)
                .plugin(PluginEntityBuilder.defaultBuilder().build());
    }

    public static WebResourceEntity.WebResourceEntityBuilder<?, ?> persistedPostfixBuilder(int postfix) {
        return postfixBuilder(postfix)
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
