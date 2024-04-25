package com.victor.kochnev.core.service.task;

import com.victor.kochnev.core.dto.domain.entity.TaskDto;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;
import com.victor.kochnev.core.dto.request.GetTasksRequestDto;
import com.victor.kochnev.core.dto.request.MakeDecisionRequestDto;
import com.victor.kochnev.core.dto.response.GetTasksResponseDto;
import com.victor.kochnev.domain.enums.TaskType;

import java.util.UUID;

public interface TaskService {
    TaskDto create(CreateTaskRequestDto requestDto, TaskType type);

    TaskDto makeDecision(UUID taskId, MakeDecisionRequestDto requestDto);

    GetTasksResponseDto getByFilters(GetTasksRequestDto requestDto);
}
