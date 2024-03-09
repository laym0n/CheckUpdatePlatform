package com.victor.kochnev.integration.plugin.api.dto;

public class WebResourceDtoBuilder {
    public static final String DEFAULT_NAME = "name";
    public static final String DEFAULT_DESCRIPTION = "description";

    private WebResourceDtoBuilder() {
    }

    public static WebResourceDto defaultWebResourceDto() {
        WebResourceDto webResourceDto = new WebResourceDto();
        webResourceDto.setName(DEFAULT_NAME);
        webResourceDto.setDescription(DEFAULT_DESCRIPTION);
        return webResourceDto;
    }
}
