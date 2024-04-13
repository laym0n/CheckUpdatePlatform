package com.victor.kochnev.domain.value.object;

import java.util.List;

public class PluginSpecificDescriptionBuilder {
    private PluginSpecificDescriptionBuilder() {
    }

    public static PluginSpecificDescription.PluginSpecificDescriptionBuilder<?, ?> defaultBuilder() {
        return PluginSpecificDescription.builder()
                .imagePaths(List.of("example"))
                .description("description")
                .tags(List.of("tag1", "tag2"));
    }
}
