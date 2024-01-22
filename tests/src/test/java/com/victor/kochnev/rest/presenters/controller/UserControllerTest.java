package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.core.dto.request.UserRegistrationRequestDto;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.entity.builder.UserEntityBuilder;
import com.victor.kochnev.domain.entity.builder.UserDomainBuilder;
import com.victor.kochnev.domain.enums.UserRole;
import com.victor.kochnev.tests.base.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserControllerTest extends BaseControllerTest {
    private static final String USER_REGISTRATION_ENDPOINT = "/user/register";
    private static final String REQUEST_EMAIL = UserDomainBuilder.DEFAULT_EMAIL;
    private static final String REQUEST_PASSWORD = "password";
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static UserRegistrationRequestDto prepareRequest() {
        UserRegistrationRequestDto request = new UserRegistrationRequestDto();
        request.setEmail(REQUEST_EMAIL);
        request.setPassword(REQUEST_PASSWORD);
        return request;
    }

    @Test
    void testSuccessRegistration() {
        //Assign
        UserRegistrationRequestDto request = prepareRequest();

        //Action
        MvcResult result = post(USER_REGISTRATION_ENDPOINT, request);

        //Assert
        assertHttpStatusOk(result);

        Optional<UserEntity> optionalCreatedUser = userEntityRepository.findByEmail(REQUEST_EMAIL);
        assertTrue(optionalCreatedUser.isPresent());
        UserEntity createdUser = optionalCreatedUser.get();
        assertEquals(REQUEST_EMAIL, createdUser.getEmail());
        assertTrue(passwordEncoder.matches(REQUEST_PASSWORD, createdUser.getPassword()));
        assertTrue(createdUser.isEnabled());
        assertEquals(UserRole.SIMPLE_USER.name(), createdUser.getRoles());
    }

    @Test
    void testRegistration_WithAlreadyExistEmail_Expect409() {
        //Assign
        userEntityRepository.save(UserEntityBuilder.defaultEntityUser().email(REQUEST_EMAIL).build());
        UserRegistrationRequestDto request = prepareRequest();

        //Action
        MvcResult result = post(USER_REGISTRATION_ENDPOINT, request);

        //Assert
        assertHttpStatus(result, HttpStatus.CONFLICT);
    }

    @Test
    void testRegistration_WithNotValidRequest_Expect400() {
        //Assign
        UserRegistrationRequestDto request = prepareRequest();
        request.setEmail(null);

        //Action
        MvcResult result = post(USER_REGISTRATION_ENDPOINT, request);

        //Assert
        assertHttpStatus(result, HttpStatus.BAD_REQUEST);
    }
}
