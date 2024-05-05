package com.victor.kochnev.core.dto.dal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetFeedbacksDalRequestDto {
    private FeedbacksFilterDalDto filters;

    public FeedbacksFilterDalDto getFilters() {
        return filters = filters == null ? new FeedbacksFilterDalDto() : filters;
    }
}
