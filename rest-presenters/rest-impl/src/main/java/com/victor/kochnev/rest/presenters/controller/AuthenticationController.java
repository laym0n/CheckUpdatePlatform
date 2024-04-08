package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.api.dto.AuthenticateResponse;
import com.victor.kochnev.api.dto.AuthenticationRefreshRequest;
import com.victor.kochnev.api.dto.AuthenticationRequest;
import com.victor.kochnev.api.rest.AuthenticationApi;
import com.victor.kochnev.rest.presenters.security.service.AuthenticationService;
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
public class AuthenticationController implements AuthenticationApi {
    private static final String AUTHENTICATION_ENDPOINT = "POST /authentication";
    private static final String AUTHENTICATION_REFRESH_ENDPOINT = "POST /authentication/refresh";
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AuthenticateResponse> authentication(AuthenticationRequest requestBody) {
        log.info("Request: {}", AUTHENTICATION_ENDPOINT);
        log.debug("Request: {} {}", AUTHENTICATION_ENDPOINT, requestBody);

        var authenticationResponse = authenticationService.authenticate(requestBody);

        log.info("Request: {} proccesed", AUTHENTICATION_ENDPOINT);
        return ResponseEntity.ok(authenticationResponse);
    }

    @Override
    public ResponseEntity<AuthenticateResponse> authenticationRefresh(AuthenticationRefreshRequest request) {
        log.info("Request: {}", AUTHENTICATION_REFRESH_ENDPOINT);
        log.debug("Request: {} {}", AUTHENTICATION_REFRESH_ENDPOINT, request);

        var authenticationResponse = authenticationService.refresh(request);

        log.info("Request: {} proccesed", AUTHENTICATION_REFRESH_ENDPOINT);
        return ResponseEntity.ok(authenticationResponse);
    }

}
