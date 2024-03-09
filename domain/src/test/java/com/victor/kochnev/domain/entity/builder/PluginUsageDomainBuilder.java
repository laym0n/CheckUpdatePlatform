package com.victor.kochnev.domain.entity.builder;

import com.victor.kochnev.domain.entity.PluginUsage;
import com.victor.kochnev.domain.value.object.DistributionMethodBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;

public class PluginUsageDomainBuilder {

    private PluginUsageDomainBuilder() {
    }

    public static PluginUsage.PluginUsageBuilder<?, ?> defaultPluginUsage() {
        return PluginUsage.builder()
                .plugin(PluginDomainBuilder.persistedDefaultUser().build())
                .user(UserDomainBuilder.persistedDefaultUser().build())
                .distributionMethod(DistributionMethodBuilder.defaultPurchaseDistribution());
    }

    public static PluginUsage.PluginUsageBuilder<?, ?> persistedDefaultUser() {
        return defaultPluginUsage()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }

    public static PluginUsage.PluginUsageBuilder<?, ?> purchaseUsage() {
        return persistedDefaultUser()
                .distributionMethod(DistributionMethodBuilder.defaultPurchaseDistribution());
    }
}

