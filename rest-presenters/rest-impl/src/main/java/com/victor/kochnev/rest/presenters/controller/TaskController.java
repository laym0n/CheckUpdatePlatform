package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.api.dto.CreateTaskRequest;
import com.victor.kochnev.api.dto.MakeDecisionRequest;
import com.victor.kochnev.api.dto.TaskDto;
import com.victor.kochnev.api.rest.TaskApi;
import com.victor.kochnev.core.dto.domain.entity.TaskDomainDto;
import com.victor.kochnev.core.facade.task.TaskFacade;
import com.victor.kochnev.rest.presenters.converter.RestTaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
@Slf4j
public class TaskController implements TaskApi {
    private static final String CREATE_TASK_ENDPOINT = "POST /task";
    private static final String MAKE_DECISION_ENDPOINT = "POST /task/{entityId}/decision";
    private final TaskFacade taskFacade;
    private final RestTaskMapper taskMapper;

    @Override
    public ResponseEntity<TaskDto> createTask(CreateTaskRequest requestBody) {
        log.info("Request: {}", CREATE_TASK_ENDPOINT);
        log.debug("Request: {} {}", CREATE_TASK_ENDPOINT, requestBody);

        var requestDto = taskMapper.mapToCoreRequest(requestBody);
        TaskDomainDto taskDomainDto = taskFacade.create(requestDto);
        TaskDto task = taskMapper.mapToRestDto(taskDomainDto);

        log.info("Request: {} proccesed", CREATE_TASK_ENDPOINT);
        return ResponseEntity.ok(task);
    }

    @Override
    public ResponseEntity<TaskDto> makeDecision(UUID taskId, MakeDecisionRequest requestBody) {
        log.info("Request: {}", MAKE_DECISION_ENDPOINT);
        log.debug("Request: {} {}", MAKE_DECISION_ENDPOINT, requestBody);

        var requestDto = taskMapper.mapToCoreRequest(requestBody);
        requestDto.setTaskId(taskId);
        TaskDomainDto taskDomainDto = taskFacade.makeDecision(requestDto);
        TaskDto task = taskMapper.mapToRestDto(taskDomainDto);

        log.info("Request: {} proccesed", MAKE_DECISION_ENDPOINT);
        return ResponseEntity.ok(task);
    }
}
