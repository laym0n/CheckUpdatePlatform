package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDomainDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequestDto;
import com.victor.kochnev.core.dto.request.StopWebResourceObservingRequestDto;
import com.victor.kochnev.core.facade.webresourceobserving.WebResourceObservingFacade;
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
@Tag(name = "WebResourceObserving")
public class WebResourceObservingController {
    private static final String WEB_RESOURCE_OBSERVING_CREATE_ENDPOINT = "POST /webresource/observing";
    private static final String WEB_RESOURCE_OBSERVING_STOP_ENDPOINT = "PUT /webresource/observing/stop";
    private final WebResourceObservingFacade webResourceObservingFacade;

    @PostMapping("/webresource/observing")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(operationId = "createObserving")
    public ResponseEntity<WebResourceObservingDomainDto> createObserving(@Valid @RequestBody AddWebResourceForObservingRequestDto request) {
        log.info("Request: {}", WEB_RESOURCE_OBSERVING_CREATE_ENDPOINT);
        log.debug("Request: {} {}", WEB_RESOURCE_OBSERVING_CREATE_ENDPOINT, request);

        WebResourceObservingDomainDto webResourceObservingDomainDto = webResourceObservingFacade.addWebResourceForObserving(request);

        log.info("Request: {} proccesed", WEB_RESOURCE_OBSERVING_CREATE_ENDPOINT);
        return ResponseEntity.ok(webResourceObservingDomainDto);
    }

    @PutMapping("/webresource/observing/{id}/stop")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(operationId = "stopObserve")
    public ResponseEntity<WebResourceObservingDomainDto> stopObserve(@Valid @PathVariable("id") UUID observingId) {
        log.info("Request: {}", WEB_RESOURCE_OBSERVING_STOP_ENDPOINT);
        log.debug("Request: {} {}", WEB_RESOURCE_OBSERVING_STOP_ENDPOINT, observingId);

        StopWebResourceObservingRequestDto request = new StopWebResourceObservingRequestDto(observingId);
        WebResourceObservingDomainDto webResourceObservingDomainDto = webResourceObservingFacade.stopWebResourceObserving(request);

        log.info("Request: {} proccesed", WEB_RESOURCE_OBSERVING_STOP_ENDPOINT);
        return ResponseEntity.ok(webResourceObservingDomainDto);
    }
}
