package com.victor.kochnev.integration.plugin.api.dto;

public class WebResourceDtoBuilder {
    public static final String DEFAULT_NAME = "name";
    public static final String DEFAULT_DESCRIPTION = "description";
    public static final String DEFAULT_DESCRIPTION_HEADER = "descriptionHeader";

    private WebResourceDtoBuilder() {
    }

    public static WebResourceDto defaultWebResourceDto() {
        WebResourceDto webResourceDto = new WebResourceDto();
        webResourceDto.setName(DEFAULT_NAME);
        webResourceDto.setDescription(DEFAULT_DESCRIPTION);
        webResourceDto.setDescriptionHeader(DEFAULT_DESCRIPTION_HEADER);
        return webResourceDto;
    }
}
