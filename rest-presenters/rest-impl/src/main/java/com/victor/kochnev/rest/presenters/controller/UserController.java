package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.api.dto.UserRegistrationRequest;
import com.victor.kochnev.api.rest.UserApi;
import com.victor.kochnev.core.dto.request.UserRegistrationRequestDto;
import com.victor.kochnev.core.facade.user.UserFacade;
import com.victor.kochnev.rest.presenters.converter.RestUserRequestMapper;
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
public class UserController implements UserApi {
    private static final String USER_REGISTER_ENDPOINT = "POST /user/register";
    private final UserFacade userFacade;
    private final RestUserRequestMapper restUserRequestMapper;

    @Override
    public ResponseEntity<Void> register(UserRegistrationRequest requestBody) {
        log.info("Request: {}", USER_REGISTER_ENDPOINT);
        log.debug("Request: {} {}", USER_REGISTER_ENDPOINT, requestBody);

        UserRegistrationRequestDto registrationRequest = restUserRequestMapper.mapToCoreRequest(requestBody);
        userFacade.registerUser(registrationRequest);

        log.info("Request: {} proccesed", USER_REGISTER_ENDPOINT);
        return ResponseEntity.ok().build();
    }

}
