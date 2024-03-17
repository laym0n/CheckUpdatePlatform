package com.victor.kochnev.domain.entity.builder;

import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.value.object.PluginDescriptionBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;

public class PluginDomainBuilder {

    private PluginDomainBuilder() {
    }

    public static Plugin.PluginBuilder<?, ?> defaultPlugin() {
        return Plugin.builder()
                .name("name")
                .baseUrl("baseUrl")
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
