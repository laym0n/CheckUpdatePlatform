package com.victor.kochnev.dal.entity;

import com.victor.kochnev.dal.embeddable.object.EmbeddableDistributionMethodBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;

public class PluginUsageEntityBuilder {

    private PluginUsageEntityBuilder() {
    }

    public static PluginUsageEntity.PluginUsageEntityBuilder<?, ?> defaultPluginUsage() {
        return PluginUsageEntity.builder()
                .plugin(PluginEntityBuilder.defaultBuilder().build())
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

    public static PluginUsageEntity.PluginUsageEntityBuilder<?, ?> persistedBuilder(int postfix) {
        return PluginUsageEntity.builder()
                .plugin(PluginEntityBuilder.defaultBuilder().build())
                .user(UserEntityBuilder.defaultBuilder().build())
                .distributionMethod(EmbeddableDistributionMethodBuilder.defaultPurchaseDistribution().build());
    }

    public static PluginUsageEntity.PluginUsageEntityBuilder<?, ?> persistedPostfixBuilder(int postfix) {
        return persistedBuilder(postfix)
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
