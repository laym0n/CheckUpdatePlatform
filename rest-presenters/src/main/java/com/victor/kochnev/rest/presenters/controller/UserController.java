package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.core.dto.UserRegistrationRequestDto;
import com.victor.kochnev.core.facade.user.UserFacade;
import com.victor.kochnev.rest.presenters.api.UserApi;
import com.victor.kochnev.rest.presenters.api.dto.UserRegistrationRequestBody;
import com.victor.kochnev.rest.presenters.converter.UserRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApi {
    private static final String USER_REGISTER_ENDPOINT = "POST /user/register";
    private final UserFacade userFacade;
    private final UserRequestMapper userRequestMapper;

    @Override
    public ResponseEntity<Void> register(UserRegistrationRequestBody requestBody) {
        log.info("Request: {}", USER_REGISTER_ENDPOINT);
        log.debug("Request: {} {}", USER_REGISTER_ENDPOINT, requestBody);

        UserRegistrationRequestDto registrationRequest = userRequestMapper.mapToUserRegistrationRequestDto(requestBody);
        userFacade.registerUser(registrationRequest);

        log.info("Request: {} proccesed", USER_REGISTER_ENDPOINT);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("sign/in1")
//    public String sdfs() {
//        return "sdfsfd";
//    }

}
