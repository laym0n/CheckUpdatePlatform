package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.core.dto.domain.entity.PluginUsageDomainDto;
import com.victor.kochnev.core.dto.request.CreatePluginUsageRequestDto;
import com.victor.kochnev.core.facade.pluginusage.PluginUsageFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
@Slf4j
@Tag(name = "PluginUsage")
public class PluginUsageController {
    private static final String CREATE_PLUGIN_USAGE_ENDPOINT = "POST /plugin/usage";
    private final PluginUsageFacade pluginUsageFacade;

    @PostMapping("/plugin/usage")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(operationId = "createPluginUsage")
    public ResponseEntity<PluginUsageDomainDto> createPluginUsage(@Valid @RequestBody CreatePluginUsageRequestDto request) {
        log.info("Request: {}", CREATE_PLUGIN_USAGE_ENDPOINT);
        log.debug("Request: {} {}", CREATE_PLUGIN_USAGE_ENDPOINT, request);

        PluginUsageDomainDto pluginUsageDomainDto = pluginUsageFacade.create(request);

        log.info("Request: {} proccesed", CREATE_PLUGIN_USAGE_ENDPOINT);
        return ResponseEntity.ok(pluginUsageDomainDto);
    }
}
