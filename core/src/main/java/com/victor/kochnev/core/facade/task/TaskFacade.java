package com.victor.kochnev.core.facade.task;

import com.victor.kochnev.core.dto.domain.entity.TaskDomainDto;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;
import com.victor.kochnev.core.dto.request.MakeDecisionRequestDto;

public interface TaskFacade {

    TaskDomainDto create(CreateTaskRequestDto requestDto);

    TaskDomainDto makeDecision(MakeDecisionRequestDto requestDto);
}
