package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.api.dto.AuthenticationRequestBody;
import com.victor.kochnev.api.dto.ErrorMessageDto;
import com.victor.kochnev.api.dto.JwtToken;
import com.victor.kochnev.core.service.user.PasswordCoder;
import com.victor.kochnev.dal.entity.builder.UserEntityBuilder;
import com.victor.kochnev.domain.entity.builder.UserDomainBuilder;
import com.victor.kochnev.rest.presenters.security.service.JwtService;
import com.victor.kochnev.tests.base.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthenticationControllerTest extends BaseControllerTest {
    private static final String AUTHENTICATION_ENDPOINT = "/authentication";
    private static final String REQUEST_EMAIL = UserDomainBuilder.DEFAULT_EMAIL;
    private static final String REQUEST_PASSWORD = UserDomainBuilder.DEFAULT_PASSWORD;

    @Autowired
    PasswordCoder passwordCoder;
    @Autowired
    JwtService jwtService;

    @Test
    void testSuccessAuthentication() {
        //Assign
        userEntityRepository.save(UserEntityBuilder
                .defaultEntityUser()
                .email(REQUEST_EMAIL)
                .password(passwordCoder.encode(REQUEST_PASSWORD))
                .build());
        var request = prepareRequest();

        //Action
        MvcResult result = post(AUTHENTICATION_ENDPOINT, request);

        //Assert
        assertHttpStatusOk(result);

        JwtToken responseDto = getResponseDto(result, JwtToken.class);
        assertNotNull(responseDto.getToken());

        String usernameFromToken = jwtService.getUsernameFromToken(responseDto.getToken());
        assertEquals(REQUEST_EMAIL, usernameFromToken);
    }

    @Test
    void testBadCredentialsPasswordAuthentication_expect400() {
        //Assign
        userEntityRepository.save(UserEntityBuilder
                .defaultEntityUser()
                .email(REQUEST_EMAIL)
                .password(passwordCoder.encode(REQUEST_PASSWORD + "1"))
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
        userEntityRepository.save(UserEntityBuilder
                .defaultEntityUser()
                .email(REQUEST_EMAIL + "1")
                .password(passwordCoder.encode(REQUEST_PASSWORD))
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
        userEntityRepository.save(UserEntityBuilder
                .defaultEntityUser()
                .email(REQUEST_EMAIL + "1")
                .build());
        var request = prepareRequest();

        //Action
        MvcResult result = post(AUTHENTICATION_ENDPOINT, request);

        //Assert
        assertHttpStatus(result, HttpStatus.BAD_REQUEST);
    }

    private AuthenticationRequestBody prepareRequest() {
        AuthenticationRequestBody request = new AuthenticationRequestBody();
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
