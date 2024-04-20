package com.victor.kochnev.core.dto.dal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPluginsDalRequestDto {
    private PluginsFilterDalDto filters;

    public PluginsFilterDalDto getFilters() {
        return filters = filters == null ? new PluginsFilterDalDto() : filters;
    }
}
