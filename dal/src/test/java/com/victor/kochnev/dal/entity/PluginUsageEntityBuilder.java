package com.victor.kochnev.dal.entity;

import com.victor.kochnev.dal.embeddable.object.EmbeddableDistributionMethodBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;

public class PluginUsageEntityBuilder {

    private PluginUsageEntityBuilder() {
    }

    public static PluginUsageEntity.PluginUsageEntityBuilder<?, ?> defaultPluginUsage() {
        return PluginUsageEntity.builder()
                .plugin(PluginEntityBuilder.defaultPlugin().build())
                .user(UserEntityBuilder.defaultBuilder().build())
                .distributionMethod(EmbeddableDistributionMethodBuilder.defaultPurchaseDistribution().build());
    }

    public static PluginUsageEntity.PluginUsageEntityBuilder<?, ?> persistedDefaultBuilder() {
        return defaultPluginUsage()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
