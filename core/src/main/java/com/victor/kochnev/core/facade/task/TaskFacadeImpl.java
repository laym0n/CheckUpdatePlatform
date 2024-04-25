package com.victor.kochnev.core.facade.task;

import com.victor.kochnev.core.dto.domain.entity.TaskDto;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;
import com.victor.kochnev.core.dto.request.GetTasksRequestDto;
import com.victor.kochnev.core.dto.request.MakeDecisionRequestDto;
import com.victor.kochnev.core.dto.response.GetTasksResponseDto;
import com.victor.kochnev.core.service.plugin.PluginService;
import com.victor.kochnev.core.service.task.TaskService;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.enums.PluginStatus;
import com.victor.kochnev.domain.enums.TaskType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TaskFacadeImpl implements TaskFacade {
    private final TaskService taskService;
    private final PluginService pluginService;

    @PreAuthorize("@authorizationService.verifyAuthenticatedUserCanManagePlugin(#requestDto.getPluginId())")
    @Override
    public TaskDto create(@P("requestDto") CreateTaskRequestDto requestDto) {
        Plugin plugin = pluginService.getById(requestDto.getPluginId());
        TaskType taskType;
        if (PluginStatus.CREATED.equals(plugin.getStatus())) {
            taskType = TaskType.INITIALIZE;
        } else {
            taskType = TaskType.UPDATE;
        }
        return taskService.create(requestDto, taskType);
    }

    @Override
    public TaskDto makeDecision(UUID taskId, MakeDecisionRequestDto requestDto) {
        return taskService.makeDecision(taskId, requestDto);
    }

    @Override
    public GetTasksResponseDto get(GetTasksRequestDto requestDto) {
        return taskService.getByFilters(requestDto);
    }
}
