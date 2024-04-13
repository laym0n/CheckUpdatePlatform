package com.victor.kochnev.dal.embeddable.object;

import com.victor.kochnev.domain.value.object.DistributionMethod;
import com.victor.kochnev.domain.value.object.DistributionMethodBuilder;

import java.util.List;

public class EmbeddablePluginDescriptionBuilder {
    public static final String DEFAULT_LOGO_PATH = "description";
    public static final List<DistributionMethod> DEFAULT_DISTRIBUTION_METHODS_COLLECTION = List.of(DistributionMethodBuilder.defaultPurchaseDistribution(), DistributionMethodBuilder.defaultSubscribeDistribution());

    private EmbeddablePluginDescriptionBuilder() {

    }

    public static EmbeddablePluginDescription.EmbeddablePluginDescriptionBuilder defaultBuilder() {
        return EmbeddablePluginDescription.builder()
                .logoPath(DEFAULT_LOGO_PATH)
                .distributionMethods(DEFAULT_DISTRIBUTION_METHODS_COLLECTION)
                .specificDescription(EmbeddableSpecificPluginDescriptionBuilder.defaultBuilder().build());
    }
}
