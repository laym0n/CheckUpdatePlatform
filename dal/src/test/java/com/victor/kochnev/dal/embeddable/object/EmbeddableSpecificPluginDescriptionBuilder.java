package com.victor.kochnev.dal.embeddable.object;

import com.victor.kochnev.dal.entity.value.object.TagsInfo;

import java.util.List;

public class EmbeddableSpecificPluginDescriptionBuilder {
    public static final String DEFAULT_DESCRIPTION = "description";
    public static final List<String> DEFAULT_IMAGE_PATHS = List.of("path1", "path2");
    public static final List<String> DEFAULT_TAGS = List.of("tag1", "tag2");

    private EmbeddableSpecificPluginDescriptionBuilder() {

    }

    public static EmbeddableSpecificPluginDescription.EmbeddableSpecificPluginDescriptionBuilder defaultBuilder() {
        return EmbeddableSpecificPluginDescription.builder()
                .description(DEFAULT_DESCRIPTION)
                .imagePaths(DEFAULT_IMAGE_PATHS)
                .tags(new TagsInfo(DEFAULT_TAGS));
    }
}
