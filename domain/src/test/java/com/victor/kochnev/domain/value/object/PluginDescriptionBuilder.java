package com.victor.kochnev.domain.value.object;

import java.util.List;

public class PluginDescriptionBuilder {
    public static final String DEFAULT_LOGO_PATH = "logoPath";

    private PluginDescriptionBuilder() {
    }

    public static PluginDescription.PluginDescriptionBuilder<?, ?> defaultBuilder() {
        return PluginDescription.builder()
                .logoPath(DEFAULT_LOGO_PATH)
                .distributionMethods(List.of(DistributionMethodBuilder.defaultPurchaseDistribution(), DistributionMethodBuilder.defaultSubscribeDistribution()))
                .specificDescription(PluginSpecificDescriptionBuilder.defaultBuilder().build());
    }
}
