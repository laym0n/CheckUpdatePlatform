package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.api.dto.WebResourceObserving;
import com.victor.kochnev.api.dto.WebResourceObservingAddRequestBody;
import com.victor.kochnev.api.dto.WebResourceObservingStopRequestBody;
import com.victor.kochnev.api.rest.WebResourceObservingApi;
import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequest;
import com.victor.kochnev.core.dto.request.StopWebResourceObservingRequest;
import com.victor.kochnev.core.facade.webresourceobserving.WebResourceObservingFacade;
import com.victor.kochnev.rest.presenters.converter.WebResourceObservingDtoMapper;
import com.victor.kochnev.rest.presenters.converter.WebResourceObservingRequestMapper;
import com.victor.kochnev.rest.presenters.security.entity.UserSecurity;
import com.victor.kochnev.rest.presenters.security.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
public class WebResourceObservingController implements WebResourceObservingApi {
    private static final String WEB_RESOURCE_OBSERVING_CREATE_ENDPOINT = "POST /webresource/observing";
    private static final String WEB_RESOURCE_OBSERVING_STOP_ENDPOINT = "PUT /webresource/observing/stop";
    private final WebResourceObservingFacade webResourceObservingFacade;
    private final WebResourceObservingRequestMapper requestMapper;
    private final WebResourceObservingDtoMapper dtoMapper;
    private final SecurityUserService securityUserService;

    @Override
    public ResponseEntity<WebResourceObserving> create(WebResourceObservingAddRequestBody requestBody) {
        log.info("Request: {}", WEB_RESOURCE_OBSERVING_CREATE_ENDPOINT);
        log.debug("Request: {} {}", WEB_RESOURCE_OBSERVING_CREATE_ENDPOINT, requestBody);

        AddWebResourceForObservingRequest request = requestMapper.mapToCoreRequest(requestBody);
        UserSecurity currentUser = securityUserService.getCurrentUser();
        request.setUserId(currentUser.getId());

        WebResourceObservingDto webResourceObservingDto = webResourceObservingFacade.addWebResourceForObserving(request);
        WebResourceObserving webResourceObserving = dtoMapper.mapToRestDto(webResourceObservingDto);

        log.info("Request: {} proccesed", WEB_RESOURCE_OBSERVING_CREATE_ENDPOINT);
        return ResponseEntity.ok(webResourceObserving);
    }

    @Override
    public ResponseEntity<WebResourceObserving> stopObserve(WebResourceObservingStopRequestBody requestBody) {
        log.info("Request: {}", WEB_RESOURCE_OBSERVING_STOP_ENDPOINT);
        log.debug("Request: {} {}", WEB_RESOURCE_OBSERVING_STOP_ENDPOINT, requestBody);

        StopWebResourceObservingRequest request = requestMapper.mapToCoreRequest(requestBody);
        UserSecurity currentUser = securityUserService.getCurrentUser();
        request.setUserId(currentUser.getId());

        WebResourceObservingDto webResourceObservingDto = webResourceObservingFacade.stopWebResourceObserving(request);
        WebResourceObserving webResourceObserving = dtoMapper.mapToRestDto(webResourceObservingDto);

        log.info("Request: {} proccesed", WEB_RESOURCE_OBSERVING_STOP_ENDPOINT);
        return ResponseEntity.ok(webResourceObserving);
    }
}
