package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.core.dto.domain.entity.PluginUsageDto;
import com.victor.kochnev.core.dto.request.CreatePluginUsageRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginUsagesRequestDto;
import com.victor.kochnev.core.dto.response.GetPluginUsagesResponseDto;
import com.victor.kochnev.core.facade.pluginusage.PluginUsageFacade;
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

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
@Slf4j
@Tag(name = "PluginUsage")
public class PluginUsageController {
    private static final String CREATE_PLUGIN_USAGE_ENDPOINT = "POST /plugin/usage";
    private static final String GET_PLUGIN_USAGES_ENDPOINT = "GET /plugin/usage";
    private final PluginUsageFacade pluginUsageFacade;

    @PostMapping("/plugin/usage")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(operationId = "createPluginUsage")
    public ResponseEntity<PluginUsageDto> createPluginUsage(@Valid @RequestBody @NotNull CreatePluginUsageRequestDto request) {
        log.info("Request: {}", CREATE_PLUGIN_USAGE_ENDPOINT);
        log.debug("Request: {} {}", CREATE_PLUGIN_USAGE_ENDPOINT, request);

        PluginUsageDto pluginUsageDto = pluginUsageFacade.create(request);

        log.info("Request: {} proccesed", CREATE_PLUGIN_USAGE_ENDPOINT);
        return ResponseEntity.ok(pluginUsageDto);
    }

    @GetMapping("/plugin/usage")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            operationId = "getPluginUsages",
            parameters = {
                    @Parameter(name = "filters.pluginIds", array = @ArraySchema(schema = @Schema(type = "string"))),
            }
    )
    public ResponseEntity<GetPluginUsagesResponseDto> get(@Parameter(hidden = true) @Valid @Nullable GetPluginUsagesRequestDto request) {
        log.info("Request: {}", GET_PLUGIN_USAGES_ENDPOINT);
        log.debug("Request: {} {}", GET_PLUGIN_USAGES_ENDPOINT, request);

        var responseDto = pluginUsageFacade.getByFilters(request);

        log.info("Request: {} proccesed", GET_PLUGIN_USAGES_ENDPOINT);
        return ResponseEntity.ok(responseDto);
    }
}
