package com.victor.kochnev.core.service.task;

import com.victor.kochnev.core.dto.domain.entity.TaskDto;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;
import com.victor.kochnev.core.dto.request.GetTasksRequestDto;
import com.victor.kochnev.core.dto.request.MakeDecisionRequestDto;
import com.victor.kochnev.core.dto.response.GetTasksResponseDto;
import com.victor.kochnev.domain.entity.Task;
import com.victor.kochnev.domain.enums.TaskType;

import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    TaskDto create(CreateTaskRequestDto requestDto, TaskType type);

    TaskDto makeDecision(UUID taskId, MakeDecisionRequestDto requestDto);

    GetTasksResponseDto getByFiltersForCurrentUser(GetTasksRequestDto requestDto);

    Optional<Task> findNotResolvedByPluginId(UUID pluginId);

    TaskDto update(UUID id, CreateTaskRequestDto requestDto);
}
