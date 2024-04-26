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
public class GetTasksRequestDto {
    @Nullable
    private TasksFilterDto filters;

    @Nullable
    public TasksFilterDto getFilters() {
        return filters = filters == null ? new TasksFilterDto() : filters;
    }
}
