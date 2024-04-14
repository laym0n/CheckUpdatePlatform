package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.core.dto.request.UserRegistrationRequestDto;
import com.victor.kochnev.core.facade.user.UserFacade;
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
@Tag(name = "User")
public class UserController {
    private static final String USER_REGISTER_ENDPOINT = "POST /user/register";
    private final UserFacade userFacade;

    @PostMapping("/user/register")
    @Operation(operationId = "register")
    public ResponseEntity<Void> register(@Valid @RequestBody @NotNull UserRegistrationRequestDto requestBody) {
        log.info("Request: {}", USER_REGISTER_ENDPOINT);
        log.debug("Request: {} {}", USER_REGISTER_ENDPOINT, requestBody);

        userFacade.registerUser(requestBody);

        log.info("Request: {} proccesed", USER_REGISTER_ENDPOINT);
        return ResponseEntity.ok().build();
    }

}
