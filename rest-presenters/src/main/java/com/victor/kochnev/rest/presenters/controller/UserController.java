package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.api.SignApi;
import com.victor.kochnev.core.dto.UserRegistrationRequestDto;
import com.victor.kochnev.core.facade.user.UserFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements SignApi {
    private static final String CREATE_USER_ENDPOINT = "POST /sign/up";
    private final UserFacade userFacade;

    @Override
    public ResponseEntity<Void> register(UserRegistrationRequestDto request) {
        log.info("Request: {}", CREATE_USER_ENDPOINT);
        log.debug("Request: {} {}", CREATE_USER_ENDPOINT, request);

        userFacade.registerUser(request);

        log.info("Request: {} proccesed", CREATE_USER_ENDPOINT);
        return ResponseEntity.ok().build();
    }
}
