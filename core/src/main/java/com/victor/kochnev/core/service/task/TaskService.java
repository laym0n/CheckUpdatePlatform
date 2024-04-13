package com.victor.kochnev.core.service.task;

import com.victor.kochnev.core.dto.domain.entity.TaskDomainDto;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;
import com.victor.kochnev.core.dto.request.MakeDecisionRequestDto;
import com.victor.kochnev.domain.enums.TaskType;

import java.util.UUID;

public interface TaskService {
    TaskDomainDto create(CreateTaskRequestDto requestDto, TaskType type);

    TaskDomainDto makeDecision(UUID taskId, MakeDecisionRequestDto requestDto);
}
