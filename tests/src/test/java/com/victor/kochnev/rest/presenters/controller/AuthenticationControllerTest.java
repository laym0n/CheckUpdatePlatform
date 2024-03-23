package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.api.dto.AuthenticationRequest;
import com.victor.kochnev.api.dto.ErrorMessageDto;
import com.victor.kochnev.api.dto.JwtTokenResponse;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import com.victor.kochnev.domain.entity.builder.UserDomainBuilder;
import com.victor.kochnev.rest.presenters.security.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AuthenticationControllerTest extends BaseControllerTest {
    private static final String AUTHENTICATION_ENDPOINT = "/authentication";
    private static final String REQUEST_EMAIL = UserDomainBuilder.DEFAULT_EMAIL;
    private static final String REQUEST_PASSWORD = UserDomainBuilder.DEFAULT_PASSWORD;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;

    @Test
    void testSuccessAuthentication() {
        //Assign
        userRepository.save(UserEntityBuilder
                .defaultBuilder()
                .email(REQUEST_EMAIL)
                .password(passwordEncoder.encode(REQUEST_PASSWORD))
                .build());
        var request = prepareRequest();

        //Action
        MvcResult result = post(AUTHENTICATION_ENDPOINT, request);

        //Assert
        assertHttpStatusOk(result);

        JwtTokenResponse responseDto = getResponseDto(result, JwtTokenResponse.class);
        assertNotNull(responseDto.getToken());

        String usernameFromToken = jwtService.getUsernameFromToken(responseDto.getToken());
        assertEquals(REQUEST_EMAIL, usernameFromToken);
    }

    @Test
    void testBadCredentialsPasswordAuthentication_expect400() {
        //Assign
        userRepository.save(UserEntityBuilder
                .defaultBuilder()
                .email(REQUEST_EMAIL)
                .password(passwordEncoder.encode(REQUEST_PASSWORD + "1"))
                .build());
        var request = prepareRequest();

        //Action
        MvcResult result = post(AUTHENTICATION_ENDPOINT, request);

        //Assert
        assertHttpStatus(result, HttpStatus.BAD_REQUEST);
        assertFailedAuthMessage(result);
    }

    @Test
    void testBadCredentialsEmailAuthentication_expect400() {
        //Assign
        userRepository.save(UserEntityBuilder
                .defaultBuilder()
                .email(REQUEST_EMAIL + "1")
                .password(passwordEncoder.encode(REQUEST_PASSWORD))
                .build());
        var request = prepareRequest();

        //Action
        MvcResult result = post(AUTHENTICATION_ENDPOINT, request);

        //Assert
        assertHttpStatus(result, HttpStatus.BAD_REQUEST);
        assertFailedAuthMessage(result);
    }

    @Test
    void testBadValidation_expect400() {
        //Assign
        userRepository.save(UserEntityBuilder
                .defaultBuilder()
                .email(REQUEST_EMAIL + "1")
                .build());
        var request = prepareRequest();

        //Action
        MvcResult result = post(AUTHENTICATION_ENDPOINT, request);

        //Assert
        assertHttpStatus(result, HttpStatus.BAD_REQUEST);
    }

    private AuthenticationRequest prepareRequest() {
        var request = new AuthenticationRequest();
        request.setPrincipal(REQUEST_EMAIL);
        request.setCredentials(REQUEST_PASSWORD);
        return request;
    }

    private void assertFailedAuthMessage(MvcResult result) {
        ErrorMessageDto responseDto = getResponseDto(result, ErrorMessageDto.class);
        assertNotNull(responseDto);
        assertNotNull(responseDto.getMessage());
        assertEquals("Проваленная аутентификация", responseDto.getMessage());
    }
}
