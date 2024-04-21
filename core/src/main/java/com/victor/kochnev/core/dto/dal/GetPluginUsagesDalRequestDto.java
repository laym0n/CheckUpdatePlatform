package com.victor.kochnev.core.dto.dal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPluginUsagesDalRequestDto {
    private PluginUsagesFilterDalDto filters;

    public PluginUsagesFilterDalDto getFilters() {
        return filters = filters == null ? new PluginUsagesFilterDalDto() : filters;
    }
}
