package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.core.dto.domain.entity.FeedbackDto;
import com.victor.kochnev.core.dto.request.CreateOrUpdateFeedbackRequestDto;
import com.victor.kochnev.core.dto.request.GetFeedbacksRequestDto;
import com.victor.kochnev.core.dto.response.GetFeedbacksResponseDto;
import com.victor.kochnev.core.facade.feedback.FeedbackFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Feedback")
public class FeedbackController {
    private static final String CREATE_ENDPOINT = "POST /feedback";
    private static final String GET_ENDPOINT = "GET /feedback";
    private final FeedbackFacade feedbackFacade;

    @PostMapping("/feedback")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(operationId = "createOrUpdateFeedback")
    public ResponseEntity<FeedbackDto> createOrUpdateFeedback(@Valid @RequestBody @NotNull CreateOrUpdateFeedbackRequestDto request) {
        log.info("Request: {}", CREATE_ENDPOINT);
        log.debug("Request: {} {}", CREATE_ENDPOINT, request);

        var responseDto = feedbackFacade.createOrUpdate(request);

        log.info("Request: {} proccesed {}", CREATE_ENDPOINT, responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/feedback")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(operationId = "getFeedbacks",
            parameters = {
                    @Parameter(name = "filters.ids", array = @ArraySchema(schema = @Schema(type = "string"))),
                    @Parameter(name = "filters.pluginIds", array = @ArraySchema(schema = @Schema(type = "string"))),
                    @Parameter(name = "filters.userIds", array = @ArraySchema(schema = @Schema(type = "string"))),
                    @Parameter(name = "filters.excludedUserIds", array = @ArraySchema(schema = @Schema(type = "string"))),
            })
    public ResponseEntity<GetFeedbacksResponseDto> get(@Valid @RequestBody @NotNull GetFeedbacksRequestDto request) {
        log.info("Request: {}", GET_ENDPOINT);
        log.debug("Request: {} {}", GET_ENDPOINT, request);

        var responseDto = feedbackFacade.get(request);

        log.info("Request: {} proccesed {}", GET_ENDPOINT, responseDto);
        return ResponseEntity.ok(responseDto);
    }
}
