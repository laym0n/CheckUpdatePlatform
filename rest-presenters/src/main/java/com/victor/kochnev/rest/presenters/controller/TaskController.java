package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.core.dto.domain.entity.TaskDto;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;
import com.victor.kochnev.core.dto.request.GetTasksRequestDto;
import com.victor.kochnev.core.dto.request.MakeDecisionRequestDto;
import com.victor.kochnev.core.dto.response.GetTasksResponseDto;
import com.victor.kochnev.core.facade.task.TaskFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    private static final String GET_TASKS_ENDPOINT = "GET /tasks";
    private final TaskFacade taskFacade;

    @PostMapping("/task")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(operationId = "createTask")
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody @NotNull CreateTaskRequestDto requestBody) {
        log.info("Request: {}", CREATE_TASK_ENDPOINT);
        log.debug("Request: {} {}", CREATE_TASK_ENDPOINT, requestBody);

        var taskDomainDto = taskFacade.create(requestBody);

        log.info("Request: {} proccesed", CREATE_TASK_ENDPOINT);
        return ResponseEntity.ok(taskDomainDto);
    }

    @PutMapping("/task/{id}/decision")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(operationId = "makeDecision")
    public ResponseEntity<TaskDto> makeDecision(@Valid @PathVariable("id") @NotNull UUID taskId, @Valid @RequestBody @NotNull MakeDecisionRequestDto requestBody) {
        log.info("Request: {}", MAKE_DECISION_ENDPOINT);
        log.debug("Request: {} {}", MAKE_DECISION_ENDPOINT, requestBody);

        TaskDto taskDomainDto = taskFacade.makeDecision(taskId, requestBody);

        log.info("Request: {} proccesed", MAKE_DECISION_ENDPOINT);
        return ResponseEntity.ok(taskDomainDto);
    }

    @GetMapping("/tasks")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(operationId = "getTasks",
            parameters = {
                    @Parameter(name = "filters.ids", array = @ArraySchema(schema = @Schema(type = "string"))),
                    @Parameter(name = "filters.pluginIds", array = @ArraySchema(schema = @Schema(type = "string"))),
            })
    public ResponseEntity<GetTasksResponseDto> getByFilters(@Parameter(hidden = true) @Valid @Nullable GetTasksRequestDto request) {
        log.info("Request: {}", GET_TASKS_ENDPOINT);
        log.debug("Request: {} {}", GET_TASKS_ENDPOINT, request);

        var responseDto = taskFacade.get(request);

        log.info("Request: {} proccesed", GET_TASKS_ENDPOINT);
        return ResponseEntity.ok(responseDto);
    }
}
