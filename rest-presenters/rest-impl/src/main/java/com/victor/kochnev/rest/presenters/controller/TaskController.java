package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.api.dto.CreateTaskRequest;
import com.victor.kochnev.api.dto.MakeDecisionRequest;
import com.victor.kochnev.api.dto.Task;
import com.victor.kochnev.api.rest.TaskApi;
import com.victor.kochnev.core.dto.domain.entity.TaskDto;
import com.victor.kochnev.core.facade.task.TaskFacade;
import com.victor.kochnev.rest.presenters.converter.RestTaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TaskController implements TaskApi {
    private static final String CREATE_TASK_ENDPOINT = "POST /task";
    private static final String MAKE_DECISION_ENDPOINT = "POST /task/{entityId}/decision";
    private final TaskFacade taskFacade;
    private final RestTaskMapper taskMapper;

    @Override
    public ResponseEntity<Task> createTask(CreateTaskRequest requestBody) {
        log.info("Request: {}", CREATE_TASK_ENDPOINT);
        log.debug("Request: {} {}", CREATE_TASK_ENDPOINT, requestBody);

        var requestDto = taskMapper.mapToCoreRequest(requestBody);
        TaskDto taskDto = taskFacade.create(requestDto);
        Task task = taskMapper.mapToRestDto(taskDto);

        log.info("Request: {} proccesed", CREATE_TASK_ENDPOINT);
        return ResponseEntity.ok(task);
    }

    @Override
    public ResponseEntity<Task> makeDecision(UUID taskId, MakeDecisionRequest requestBody) {
        log.info("Request: {}", MAKE_DECISION_ENDPOINT);
        log.debug("Request: {} {}", MAKE_DECISION_ENDPOINT, requestBody);

        var requestDto = taskMapper.mapToCoreRequest(requestBody);
        requestDto.setTaskId(taskId);
        TaskDto taskDto = taskFacade.makeDecision(requestDto);
        Task task = taskMapper.mapToRestDto(taskDto);

        log.info("Request: {} proccesed", MAKE_DECISION_ENDPOINT);
        return ResponseEntity.ok(task);
    }
}
