package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.api.dto.WebResourceObservingAddRequest;
import com.victor.kochnev.api.dto.WebResourceObservingDto;
import com.victor.kochnev.api.rest.WebResourceObservingApi;
import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDomainDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequestDto;
import com.victor.kochnev.core.dto.request.StopWebResourceObservingRequestDto;
import com.victor.kochnev.core.facade.webresourceobserving.WebResourceObservingFacade;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.core.security.service.user.SecurityUserService;
import com.victor.kochnev.rest.presenters.converter.RestWebResourceObservingDtoMapper;
import com.victor.kochnev.rest.presenters.converter.RestWebResourceObservingRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@CrossOrigin
@Validated
@RequiredArgsConstructor
@Slf4j
public class WebResourceObservingController implements WebResourceObservingApi {
    private static final String WEB_RESOURCE_OBSERVING_CREATE_ENDPOINT = "POST /webresource/observing";
    private static final String WEB_RESOURCE_OBSERVING_STOP_ENDPOINT = "PUT /webresource/observing/stop";
    private final WebResourceObservingFacade webResourceObservingFacade;
    private final RestWebResourceObservingRequestMapper requestMapper;
    private final RestWebResourceObservingDtoMapper dtoMapper;
    private final SecurityUserService securityUserService;

    @Override
    public ResponseEntity<WebResourceObservingDto> createObserving(WebResourceObservingAddRequest requestBody) {
        log.info("Request: {}", WEB_RESOURCE_OBSERVING_CREATE_ENDPOINT);
        log.debug("Request: {} {}", WEB_RESOURCE_OBSERVING_CREATE_ENDPOINT, requestBody);

        AddWebResourceForObservingRequestDto request = requestMapper.mapToCoreRequest(requestBody);
        UserSecurity currentUser = securityUserService.getCurrentUser();
        request.setUserId(currentUser.getId());

        WebResourceObservingDomainDto webResourceObservingDomainDto = webResourceObservingFacade.addWebResourceForObserving(request);
        WebResourceObservingDto webResourceObserving = dtoMapper.mapToRestDto(webResourceObservingDomainDto);

        log.info("Request: {} proccesed", WEB_RESOURCE_OBSERVING_CREATE_ENDPOINT);
        return ResponseEntity.ok(webResourceObserving);
    }

    @Override
    public ResponseEntity<WebResourceObservingDto> stopObserve(UUID observingId) {
        log.info("Request: {}", WEB_RESOURCE_OBSERVING_STOP_ENDPOINT);
        log.debug("Request: {} {}", WEB_RESOURCE_OBSERVING_STOP_ENDPOINT, observingId);

        UserSecurity currentUser = securityUserService.getCurrentUser();
        UUID userId = currentUser.getId();
        StopWebResourceObservingRequestDto request = new StopWebResourceObservingRequestDto(observingId, userId);

        WebResourceObservingDomainDto webResourceObservingDomainDto = webResourceObservingFacade.stopWebResourceObserving(request);
        WebResourceObservingDto webResourceObserving = dtoMapper.mapToRestDto(webResourceObservingDomainDto);

        log.info("Request: {} proccesed", WEB_RESOURCE_OBSERVING_STOP_ENDPOINT);
        return ResponseEntity.ok(webResourceObserving);
    }
}
