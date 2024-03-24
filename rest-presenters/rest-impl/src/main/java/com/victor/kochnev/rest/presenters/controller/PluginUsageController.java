package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.api.dto.CreatePluginUsageRequest;
import com.victor.kochnev.api.dto.PluginUsage;
import com.victor.kochnev.api.rest.PluginUsageApi;
import com.victor.kochnev.core.dto.domain.entity.PluginUsageDto;
import com.victor.kochnev.core.facade.pluginusage.PluginUsageFacade;
import com.victor.kochnev.rest.presenters.converter.RestPluginUsageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PluginUsageController implements PluginUsageApi {
    private static final String CREATE_PLUGIN_USAGE_ENDPOINT = "POST /plugin/usage";
    private final PluginUsageFacade pluginUsageFacade;
    private final RestPluginUsageMapper pluginUsageMapper;

    @Override
    public ResponseEntity<PluginUsage> createPluginUsage(CreatePluginUsageRequest request) {
        log.info("Request: {}", CREATE_PLUGIN_USAGE_ENDPOINT);
        log.debug("Request: {} {}", CREATE_PLUGIN_USAGE_ENDPOINT, request);

        var requestDto = pluginUsageMapper.mapToCoreRequest(request);
        PluginUsageDto pluginUsageDto = pluginUsageFacade.create(requestDto);
        PluginUsage pluginUsage = pluginUsageMapper.mapToRestDto(pluginUsageDto);

        log.info("Request: {} proccesed", CREATE_PLUGIN_USAGE_ENDPOINT);
        return ResponseEntity.ok(pluginUsage);
    }
}
