package com.victor.kochnev.domain.entity.builder;

import com.victor.kochnev.domain.entity.WebResourceObserving;
import com.victor.kochnev.domain.value.object.ObserveSettingsBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;

public class WebResourceObservingBuilder {
    private WebResourceObservingBuilder() {
    }

    public static WebResourceObserving.WebResourceObservingBuilder<?, ?> defaultBuilder() {
        return WebResourceObserving.builder()
                .observeSettings(ObserveSettingsBuilder.defaultObserveSettings())
                .webResource(WebResourceDomainBuilder.persistedDefaultBuilder().build());
    }

    public static WebResourceObserving.WebResourceObservingBuilder<?, ?> persistedDefaultBuilder() {
        return defaultBuilder()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
