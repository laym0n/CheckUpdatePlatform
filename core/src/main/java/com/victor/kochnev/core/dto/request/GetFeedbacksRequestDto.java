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
public class GetFeedbacksRequestDto {
    @Nullable
    private FeedbacksFilterDto filters;

    @Nullable
    public FeedbacksFilterDto getFilters() {
        return filters = filters == null ? new FeedbacksFilterDto() : filters;
    }
}
