package com.victor.kochnev.core.dto.plugin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CanObserveResponseDto {
    private boolean isObservable;
    private WebResourcePluginDto webResource;
}
