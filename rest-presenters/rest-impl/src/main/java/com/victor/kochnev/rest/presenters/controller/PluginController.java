package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.api.dto.AddPluginRequestBody;
import com.victor.kochnev.api.dto.Plugin;
import com.victor.kochnev.api.rest.PluginApi;
import com.victor.kochnev.core.dto.domain.entity.PluginDto;
import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.facade.plugin.PluginFacade;
import com.victor.kochnev.rest.presenters.converter.RestPluginDtoMapper;
import com.victor.kochnev.rest.presenters.converter.RestPluginRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
public class PluginController implements PluginApi {
    private static final String CREATE_PLUGIN_ENDPOINT = "POST /plugin";
    private final PluginFacade pluginFacade;
    private final RestPluginRequestMapper requestMapper;
    private final RestPluginDtoMapper restPluginDtoMapper;

    @Override
    public ResponseEntity<Plugin> createPlugin(AddPluginRequestBody requestBody) {
        log.info("Request: {}", CREATE_PLUGIN_ENDPOINT);
        log.debug("Request: {} {}", CREATE_PLUGIN_ENDPOINT, requestBody);

        AddPluginRequestDto requestDto = requestMapper.mapToCoreRequest(requestBody);
        PluginDto pluginDto = pluginFacade.addPlugin(requestDto);
        Plugin plugin = restPluginDtoMapper.mapToRestDto(pluginDto);

        log.info("Request: {} proccesed {}", CREATE_PLUGIN_ENDPOINT, pluginDto);
        return ResponseEntity.ok(plugin);
    }
}
