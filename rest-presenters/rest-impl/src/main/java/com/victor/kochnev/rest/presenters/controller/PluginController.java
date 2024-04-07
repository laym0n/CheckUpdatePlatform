package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.api.dto.AddPluginRequest;
import com.victor.kochnev.api.dto.AddPluginResponse;
import com.victor.kochnev.api.rest.PluginApi;
import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.dto.response.AddPluginResponseDto;
import com.victor.kochnev.core.facade.plugin.PluginFacade;
import com.victor.kochnev.rest.presenters.converter.RestPluginDtoMapper;
import com.victor.kochnev.rest.presenters.converter.RestPluginRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
@Slf4j
public class PluginController implements PluginApi {
    private static final String CREATE_PLUGIN_ENDPOINT = "POST /plugin";
    private final PluginFacade pluginFacade;
    private final RestPluginRequestMapper requestMapper;
    private final RestPluginDtoMapper restPluginDtoMapper;

    @Override
    public ResponseEntity<AddPluginResponse> createPlugin(AddPluginRequest requestBody) {
        log.info("Request: {}", CREATE_PLUGIN_ENDPOINT);
        log.debug("Request: {} {}", CREATE_PLUGIN_ENDPOINT, requestBody);

        AddPluginRequestDto requestDto = requestMapper.mapToCoreRequest(requestBody);
        AddPluginResponseDto addPluginResponseDto = pluginFacade.addPlugin(requestDto);
        var responseBody = restPluginDtoMapper.mapToRestDto(addPluginResponseDto);

        log.info("Request: {} proccesed {}", CREATE_PLUGIN_ENDPOINT, addPluginResponseDto);
        return ResponseEntity.ok(responseBody);
    }
}
