package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.api.dto.CreateTaskRequestBody;
import com.victor.kochnev.api.dto.Task;
import com.victor.kochnev.api.rest.TaskApi;
import com.victor.kochnev.core.dto.domain.entity.TaskDto;
import com.victor.kochnev.core.facade.task.TaskFacade;
import com.victor.kochnev.rest.presenters.converter.RestTaskMapper;
import com.victor.kochnev.rest.presenters.converter.RestTaskRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TaskController implements TaskApi {
    private static final String CREATE_TASK_ENDPOINT = "POST /task";
    private final TaskFacade taskFacade;
    private final RestTaskRequestMapper requestMapper;
    private final RestTaskMapper taskMapper;

    @Override
    public ResponseEntity<Task> createTask(CreateTaskRequestBody requestBody) {
        log.info("Request: {}", CREATE_TASK_ENDPOINT);
        log.debug("Request: {} {}", CREATE_TASK_ENDPOINT, requestBody);

        var requestDto = requestMapper.mapToCoreRequest(requestBody);
        TaskDto taskDto = taskFacade.create(requestDto);
        Task task = taskMapper.mapToRestDto(taskDto);

        log.info("Request: {} proccesed", CREATE_TASK_ENDPOINT);
        return ResponseEntity.ok(task);
    }
}
