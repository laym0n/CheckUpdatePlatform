package com.victor.kochnev.integration.plugin.api.dto;

public class CanObserveResponseBuilder {
    public static final boolean DEFAULT_IS_OBSERVABLE = true;
    public static final WebResourceDto DEFAULT_WEB_RESOURCE = WebResourceDtoBuilder.defaultWebResourceDto();

    private CanObserveResponseBuilder() {
    }

    public static CanObserveResponse defaultWebResourceDto() {
        CanObserveResponse canObserveResponse = new CanObserveResponse();
        canObserveResponse.setIsObservable(DEFAULT_IS_OBSERVABLE);
        canObserveResponse.setWebResource(DEFAULT_WEB_RESOURCE);
        return canObserveResponse;
    }
}
