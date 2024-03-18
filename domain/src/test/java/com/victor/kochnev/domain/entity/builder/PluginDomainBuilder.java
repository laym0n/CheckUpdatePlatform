package com.victor.kochnev.domain.entity.builder;

import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.value.object.PluginDescriptionBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;

public class PluginDomainBuilder {
    public static final String DEFAULT_BASE_URL = "http://localhost:9988";
    public static final String DEFAULT_NAME = "name";

    private PluginDomainBuilder() {
    }

    public static Plugin.PluginBuilder<?, ?> defaultPlugin() {
        return Plugin.builder()
                .name(DEFAULT_NAME)
                .baseUrl(DEFAULT_BASE_URL)
                .description(PluginDescriptionBuilder.defaultBuilder().build())
                .ownerUser(UserDomainBuilder.defaultUser().build());
    }

    public static Plugin.PluginBuilder<?, ?> persistedDefaultUser() {
        return defaultPlugin()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
