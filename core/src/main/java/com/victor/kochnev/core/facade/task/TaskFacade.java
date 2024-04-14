package com.victor.kochnev.core.facade.task;

import com.victor.kochnev.core.dto.domain.entity.TaskDto;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;
import com.victor.kochnev.core.dto.request.MakeDecisionRequestDto;

import java.util.UUID;

public interface TaskFacade {

    TaskDto create(CreateTaskRequestDto requestDto);

    TaskDto makeDecision(UUID taskId, MakeDecisionRequestDto requestDto);
}
