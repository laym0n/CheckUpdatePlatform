package com.victor.kochnev.dal.entity;

import com.victor.kochnev.dal.embeddable.object.EmbeddableObserveSettings;
import com.victor.kochnev.domain.enums.ObserveStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

public class WebResourceObservingEntityBuilder {
    public static final ObserveStatus DEFAULT_STATUS = ObserveStatus.OBSERVE;

    private WebResourceObservingEntityBuilder() {
    }

    public static WebResourceObservingEntity.WebResourceObservingEntityBuilder<?, ?> defaultBuilder() {
        return WebResourceObservingEntity.builder()
                .observeSettings(EmbeddableObserveSettings.builder().needNotify(true).build())
                .status(DEFAULT_STATUS)
                .webResource(WebResourceEntityBuilder.persistedDefaultBuilder().build())
                .user(UserEntityBuilder.persistedDefaultBuilder().build());
    }

    public static WebResourceObservingEntity.WebResourceObservingEntityBuilder<?, ?> persistedDefaultBuilder() {
        return defaultBuilder()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }

    public static WebResourceObservingEntity.WebResourceObservingEntityBuilder<?, ?> persistedPostfixBuilder(int postfix) {
        return defaultBuilder()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
