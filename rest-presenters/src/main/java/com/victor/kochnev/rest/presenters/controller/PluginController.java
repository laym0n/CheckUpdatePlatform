package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginsRequestDto;
import com.victor.kochnev.core.dto.response.AddPluginResponseDto;
import com.victor.kochnev.core.dto.response.GetPluginsResponseDto;
import com.victor.kochnev.core.facade.plugin.PluginFacade;
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
@Tag(name = "Plugin")
public class PluginController {
    private static final String CREATE_PLUGIN_ENDPOINT = "POST /plugin";
    private static final String GET_PLUGINS_ENDPOINT = "GET /plugin";
    private static final String GET_MY_PLUGINS_ENDPOINT = "GET /plugin/my";
    private final PluginFacade pluginFacade;

    @PostMapping("/plugin")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(operationId = "createPlugin")
    public ResponseEntity<AddPluginResponseDto> createPlugin(@Valid @RequestBody @NotNull AddPluginRequestDto request) {
        log.info("Request: {}", CREATE_PLUGIN_ENDPOINT);
        log.debug("Request: {} {}", CREATE_PLUGIN_ENDPOINT, request);

        var responseDto = pluginFacade.addPlugin(request);

        log.info("Request: {} proccesed {}", CREATE_PLUGIN_ENDPOINT, responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/plugin")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            operationId = "getPlugins",
            parameters = {
                    @Parameter(name = "filters.ids", array = @ArraySchema(schema = @Schema(type = "string"))),
                    @Parameter(name = "filters.name", schema = @Schema(type = "string")),
                    @Parameter(name = "filters.tags", array = @ArraySchema(schema = @Schema(type = "string"))),
            }
    )
    public ResponseEntity<GetPluginsResponseDto> getPlugins(@Parameter(hidden = true) @Valid @Nullable GetPluginsRequestDto request) {
        log.info("Request: {}", GET_PLUGINS_ENDPOINT);
        log.debug("Request: {} {}", GET_PLUGINS_ENDPOINT, request);

        var responseDto = pluginFacade.getPlugins(request);

        log.info("Request: {} proccesed {}", GET_PLUGINS_ENDPOINT, responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/plugin/my")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            operationId = "getMyPlugins",
            parameters = {
                    @Parameter(name = "filters.ids", array = @ArraySchema(schema = @Schema(type = "string"))),
                    @Parameter(name = "filters.name", schema = @Schema(type = "string")),
                    @Parameter(name = "filters.tags", array = @ArraySchema(schema = @Schema(type = "string"))),
            }
    )
    public ResponseEntity<GetPluginsResponseDto> getMyPlugins(@Parameter(hidden = true) @Valid @Nullable GetPluginsRequestDto request) {
        log.info("Request: {}", GET_MY_PLUGINS_ENDPOINT);
        log.debug("Request: {} {}", GET_MY_PLUGINS_ENDPOINT, request);

        var responseDto = pluginFacade.getPluginsForCurrentUser(request);

        log.info("Request: {} proccesed {}", GET_MY_PLUGINS_ENDPOINT, responseDto);
        return ResponseEntity.ok(responseDto);
    }
}
