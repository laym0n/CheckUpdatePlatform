package com.victor.kochnev.dal.embeddable.object;

import com.victor.kochnev.dal.entity.value.object.TagsInfo;
import com.victor.kochnev.domain.value.object.DistributionMethod;
import com.victor.kochnev.domain.value.object.DistributionMethodBuilder;

import java.util.List;

public class EmbeddablePluginDescriptionBuilder {
    public static final String DEFAULT_DESCRIPTION = "description";
    public static final List<String> DEFAULT_IMAGE_PATHS = List.of("path1", "path2");
    public static final List<String> DEFAULT_TAGS = List.of("tag1", "tag2");
    public static final List<DistributionMethod> DEFAULT_DISTRIBUTION_METHODS = List.of(DistributionMethodBuilder.defaultPurchaseDistribution());

    private EmbeddablePluginDescriptionBuilder() {

    }

    public static EmbeddablePluginDescription.EmbeddablePluginDescriptionBuilder defaultBuilder() {
        return EmbeddablePluginDescription.builder()
                .description(DEFAULT_DESCRIPTION)
                .imagePaths(DEFAULT_IMAGE_PATHS)
                .distributionMethods(DEFAULT_DISTRIBUTION_METHODS)
                .tags(new TagsInfo(DEFAULT_TAGS));
    }
}
