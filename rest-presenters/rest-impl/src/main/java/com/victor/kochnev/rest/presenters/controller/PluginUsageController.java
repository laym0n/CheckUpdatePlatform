package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.api.dto.CreatePluginUsageRequest;
import com.victor.kochnev.api.dto.PluginUsageDto;
import com.victor.kochnev.api.rest.PluginUsageApi;
import com.victor.kochnev.core.dto.domain.entity.PluginUsageDomainDto;
import com.victor.kochnev.core.facade.pluginusage.PluginUsageFacade;
import com.victor.kochnev.rest.presenters.converter.RestPluginUsageMapper;
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
public class PluginUsageController implements PluginUsageApi {
    private static final String CREATE_PLUGIN_USAGE_ENDPOINT = "POST /plugin/usage";
    private final PluginUsageFacade pluginUsageFacade;
    private final RestPluginUsageMapper pluginUsageMapper;

    @Override
    public ResponseEntity<PluginUsageDto> createPluginUsage(CreatePluginUsageRequest request) {
        log.info("Request: {}", CREATE_PLUGIN_USAGE_ENDPOINT);
        log.debug("Request: {} {}", CREATE_PLUGIN_USAGE_ENDPOINT, request);

        var requestDto = pluginUsageMapper.mapToCoreRequest(request);
        PluginUsageDomainDto pluginUsageDomainDto = pluginUsageFacade.create(requestDto);
        PluginUsageDto pluginUsage = pluginUsageMapper.mapToRestDto(pluginUsageDomainDto);

        log.info("Request: {} proccesed", CREATE_PLUGIN_USAGE_ENDPOINT);
        return ResponseEntity.ok(pluginUsage);
    }
}
