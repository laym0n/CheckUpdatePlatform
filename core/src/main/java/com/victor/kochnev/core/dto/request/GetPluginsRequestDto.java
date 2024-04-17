package com.victor.kochnev.core.dto.request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GetPluginsRequestDto {
    @Nullable
    private PluginsFilterDto filters;
}
