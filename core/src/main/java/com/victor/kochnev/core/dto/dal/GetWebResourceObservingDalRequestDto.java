package com.victor.kochnev.core.dto.dal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetWebResourceObservingDalRequestDto {
    private WebResourceObservingFilterDalDto filters;

    public WebResourceObservingFilterDalDto getFilters() {
        return filters = filters == null ? new WebResourceObservingFilterDalDto() : filters;
    }
}
