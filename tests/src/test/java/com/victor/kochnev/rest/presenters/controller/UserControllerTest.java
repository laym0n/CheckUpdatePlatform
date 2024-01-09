package com.victor.kochnev.rest.presenters.controller;

import builder.DomainUserBuilder;
import com.victor.kochnev.core.dto.UserRegistrationRequestDto;
import com.victor.kochnev.dal.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest extends BaseControllerTest {
    private static final String SIGN_UP_ENDPOINT = "/sign/up";
    private static final String REQUEST_EMAIL = DomainUserBuilder.DEFAULT_EMAIL;

    private static UserRegistrationRequestDto prepareRequest() {
        UserRegistrationRequestDto request = new UserRegistrationRequestDto();
        request.setEmail(REQUEST_EMAIL);
        request.setPassword("password");
        return request;
    }

    @Test
    public void test() {
        //Assign
        UserRegistrationRequestDto request = prepareRequest();

        //Action
        MvcResult result = post(SIGN_UP_ENDPOINT, request);

        //Assert
        assertHttpStatusOk(result);

        Optional<UserEntity> optionalCreatedUser = userEntityRepository.findByEmail(REQUEST_EMAIL);
        assertTrue(optionalCreatedUser.isPresent());
        UserEntity createdUser = optionalCreatedUser.get();
        assertEquals(REQUEST_EMAIL, createdUser.getEmail());
    }
}
