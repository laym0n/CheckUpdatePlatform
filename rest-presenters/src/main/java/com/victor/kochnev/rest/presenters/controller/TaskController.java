package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.core.dto.domain.entity.TaskDomainDto;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;
import com.victor.kochnev.core.dto.request.MakeDecisionRequestDto;
import com.victor.kochnev.core.facade.task.TaskFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Task")
public class TaskController {
    private static final String CREATE_TASK_ENDPOINT = "POST /task";
    private static final String MAKE_DECISION_ENDPOINT = "PUT /task/{entityId}/decision";
    private final TaskFacade taskFacade;

    @PostMapping("/task")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(operationId = "createTask")
    public ResponseEntity<TaskDomainDto> createTask(@Valid @RequestBody CreateTaskRequestDto requestBody) {
        log.info("Request: {}", CREATE_TASK_ENDPOINT);
        log.debug("Request: {} {}", CREATE_TASK_ENDPOINT, requestBody);

        var taskDomainDto = taskFacade.create(requestBody);

        log.info("Request: {} proccesed", CREATE_TASK_ENDPOINT);
        return ResponseEntity.ok(taskDomainDto);
    }

    @PutMapping("/task/{id}/decision")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(operationId = "makeDecision")
    public ResponseEntity<TaskDomainDto> makeDecision(@Valid @PathVariable("id") UUID taskId, @Valid @RequestBody MakeDecisionRequestDto requestBody) {
        log.info("Request: {}", MAKE_DECISION_ENDPOINT);
        log.debug("Request: {} {}", MAKE_DECISION_ENDPOINT, requestBody);

        TaskDomainDto taskDomainDto = taskFacade.makeDecision(taskId, requestBody);

        log.info("Request: {} proccesed", MAKE_DECISION_ENDPOINT);
        return ResponseEntity.ok(taskDomainDto);
    }
}
