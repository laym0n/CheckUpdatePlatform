package com.victor.kochnev.core.facade.task;

import com.victor.kochnev.core.dto.domain.entity.TaskDto;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;

public interface TaskFacade {

    TaskDto create(CreateTaskRequestDto requestDto);
}
