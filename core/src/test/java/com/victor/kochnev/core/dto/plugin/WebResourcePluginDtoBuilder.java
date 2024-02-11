package com.victor.kochnev.core.dto.plugin;

public class WebResourcePluginDtoBuilder {
    public static final String DEFAULT_NAME = "name";
    public static final String DEFAULT_DESCRIPTION = "description";

    private WebResourcePluginDtoBuilder() {
    }

    public static WebResourcePluginDto.WebResourcePluginDtoBuilder defaultBuilder() {
        return WebResourcePluginDto.builder()
                .description(DEFAULT_DESCRIPTION)
                .name(DEFAULT_NAME);
    }
}
