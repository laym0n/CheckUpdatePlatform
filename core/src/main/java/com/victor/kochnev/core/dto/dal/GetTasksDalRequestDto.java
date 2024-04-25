package com.victor.kochnev.core.dto.dal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTasksDalRequestDto {
    private TasksFilterDalDto filters;

    public TasksFilterDalDto getFilters() {
        return filters = filters == null ? new TasksFilterDalDto() : filters;
    }
}
