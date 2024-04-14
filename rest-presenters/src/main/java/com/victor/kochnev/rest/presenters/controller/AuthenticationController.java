package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.rest.presenters.dto.AuthenticateResponse;
import com.victor.kochnev.rest.presenters.dto.AuthenticationRefreshRequest;
import com.victor.kochnev.rest.presenters.dto.AuthenticationRequest;
import com.victor.kochnev.rest.presenters.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
@Tag(name = "Authentication")
public class AuthenticationController {
    private static final String AUTHENTICATION_ENDPOINT = "POST /authentication";
    private static final String AUTHENTICATION_REFRESH_ENDPOINT = "POST /authentication/refresh";
    private final AuthenticationService authenticationService;

    @PostMapping("/authentication")
    @Operation(operationId = "authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(@Valid @RequestBody @NotNull AuthenticationRequest requestBody) {
        log.info("Request: {}", AUTHENTICATION_ENDPOINT);
        log.debug("Request: {} {}", AUTHENTICATION_ENDPOINT, requestBody);

        var authenticationResponse = authenticationService.authenticate(requestBody);

        log.info("Request: {} proccesed", AUTHENTICATION_ENDPOINT);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/authentication/refresh")
    @Operation(operationId = "refreshAuthentication")
    public ResponseEntity<AuthenticateResponse> refreshAuthentication(@Valid @RequestBody @NotNull AuthenticationRefreshRequest request) {
        log.info("Request: {}", AUTHENTICATION_REFRESH_ENDPOINT);
        log.debug("Request: {} {}", AUTHENTICATION_REFRESH_ENDPOINT, request);

        var authenticationResponse = authenticationService.refresh(request);

        log.info("Request: {} proccesed", AUTHENTICATION_REFRESH_ENDPOINT);
        return ResponseEntity.ok(authenticationResponse);
    }

}
