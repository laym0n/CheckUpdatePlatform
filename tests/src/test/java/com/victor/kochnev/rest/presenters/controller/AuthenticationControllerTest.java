package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.api.dto.*;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.entity.builder.UserDomainBuilder;
import com.victor.kochnev.domain.enums.UserRole;
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
    private static final String AUTHENTICATION_REFRESH_ENDPOINT = "/authentication/refresh";
    private static final String REQUEST_EMAIL = UserDomainBuilder.DEFAULT_EMAIL;
    private static final String REQUEST_PASSWORD = UserDomainBuilder.DEFAULT_PASSWORD;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;

    @Test
    void testSuccessAuthentication() {
        //Assign
        UserEntity userEntity = userRepository.save(UserEntityBuilder
                .defaultBuilder()
                .email(REQUEST_EMAIL)
                .password(passwordEncoder.encode(REQUEST_PASSWORD))
                .build());
        var request = prepareRequest();

        //Action
        MvcResult result = post(AUTHENTICATION_ENDPOINT, request);

        //Assert
        assertHttpStatusOk(result);

        AuthenticateResponse responseDto = getResponseDto(result, AuthenticateResponse.class);
        assertNotNull(responseDto.getJwtToken());
        assertNotNull(responseDto.getJwtToken().getAccessToken());
        assertNotNull(responseDto.getJwtToken().getRefreshToken());

        assertNotNull(responseDto.getUser());
        assertEquals(userEntity.getId(), responseDto.getUser().getId());
        assertEquals(REQUEST_EMAIL, responseDto.getUser().getEmail());
        assertEquals(userEntity.getRoles().stream().map(UserRole::name).toList(), responseDto.getUser().getRoles().stream().map(UserRoleEnum::name).toList());

        UserSecurity user = jwtService.getUserFromToken(responseDto.getJwtToken().getAccessToken());
        assertEquals(userEntity.getId(), user.getId());
        UserSecurity user1 = jwtService.getUserFromToken(responseDto.getJwtToken().getRefreshToken());
        assertEquals(userEntity.getId(), user1.getId());
    }

    @Test
    void testSuccessRefresh() {
        //Assign
        UserEntity userEntity = userRepository.save(UserEntityBuilder
                .defaultBuilder()
                .email(REQUEST_EMAIL)
                .password(passwordEncoder.encode(REQUEST_PASSWORD))
                .build());
        User userForRefreshToken = entityUserMapper.mapToDomain(userEntity);
        UserSecurity userSecurityForRefreshToken = domainUserMapper.mapToSecurityUser(userForRefreshToken);
        String token = jwtService.generateRefreshToken(userSecurityForRefreshToken, true);
        var request = new AuthenticationRefreshRequest(token, true);

        //Action
        MvcResult result = post(AUTHENTICATION_REFRESH_ENDPOINT, request);

        //Assert
        assertHttpStatusOk(result);

        AuthenticateResponse responseDto = getResponseDto(result, AuthenticateResponse.class);
        assertNotNull(responseDto.getJwtToken());
        assertNotNull(responseDto.getJwtToken().getAccessToken());
        assertNotNull(responseDto.getJwtToken().getRefreshToken());

        assertNotNull(responseDto.getUser());
        assertEquals(userEntity.getId(), responseDto.getUser().getId());
        assertEquals(REQUEST_EMAIL, responseDto.getUser().getEmail());
        assertEquals(userEntity.getRoles().stream().map(UserRole::name).toList(), responseDto.getUser().getRoles().stream().map(UserRoleEnum::name).toList());

        UserSecurity user = jwtService.getUserFromToken(responseDto.getJwtToken().getAccessToken());
        assertEquals(userEntity.getId(), user.getId());
        UserSecurity user1 = jwtService.getUserFromToken(responseDto.getJwtToken().getRefreshToken());
        assertEquals(userEntity.getId(), user1.getId());
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
        request.setEmail(REQUEST_EMAIL);
        request.setPassword(REQUEST_PASSWORD);
        request.setRememberMe(false);
        return request;
    }

    private void assertFailedAuthMessage(MvcResult result) {
        ErrorMessageDto responseDto = getResponseDto(result, ErrorMessageDto.class);
        assertNotNull(responseDto);
        assertNotNull(responseDto.getMessage());
        assertEquals("Проваленная аутентификация", responseDto.getMessage());
    }
}
