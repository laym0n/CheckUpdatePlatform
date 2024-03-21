package com.victor.kochnev.domain.value.object;

import java.util.List;

public class PluginDescriptionBuilder {
    private PluginDescriptionBuilder() {
    }

    public static PluginDescription.PluginDescriptionBuilder<?, ?> defaultBuilder() {
        return PluginDescription.builder()
                .imagePaths(List.of("example"))
                .distributionMethods(List.of(DistributionMethodBuilder.defaultPurchaseDistribution(), DistributionMethodBuilder.defaultSubscribeDistribution()))
                .description("description");
    }
}
