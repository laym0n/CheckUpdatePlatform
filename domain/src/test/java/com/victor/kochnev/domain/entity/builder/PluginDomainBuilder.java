package com.victor.kochnev.domain.entity.builder;

import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.value.object.DistributionMethodBuilder;
import com.victor.kochnev.domain.value.object.PluginDescription;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class PluginDomainBuilder {

    private PluginDomainBuilder() {
    }

    public static Plugin.PluginBuilder<?, ?> defaultPlugin() {
        return Plugin.builder()
                .name("name")
                .baseUrl("baseUrl")
                .imagePathsList(List.of("example"))
                .description(new PluginDescription())
                .distributionMethodsCollection(List.of(DistributionMethodBuilder.defaultPurchaseDistribution(), DistributionMethodBuilder.defaultSubscribeDistribution()))
                .ownerUser(UserDomainBuilder.defaultUser().build());
//                .webResourcesCollection(List.of());
    }

    public static Plugin.PluginBuilder<?, ?> persistedDefaultUser() {
        return defaultPlugin()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
