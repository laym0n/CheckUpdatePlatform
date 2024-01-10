package com.victor.kochnev.core.service.user;

import base.BaseCoreTest;
import com.victor.kochnev.core.dto.UserRegistrationRequestDto;
import com.victor.kochnev.core.exception.UserRegistrationException;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.entity.builder.UserDomainBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest extends BaseCoreTest {
    private static final String REQUEST_EMAIL = "victor_k02@mail.ru";
    private static final String REQUEST_PASSWORD = "pass";
    @InjectMocks
    private UserServiceImpl userService;
    @Captor
    private ArgumentCaptor<User> userCaptor;

    private static UserRegistrationRequestDto prepareRequest() {
        UserRegistrationRequestDto request = new UserRegistrationRequestDto();
        request.setEmail(REQUEST_EMAIL);
        request.setPassword(REQUEST_PASSWORD);
        return request;
    }

    @Test
    public void testCreateNotExistedUser() {
        //Assign
        when(userRepository.findUserByEmail(eq(REQUEST_EMAIL))).thenReturn(Optional.empty());
        UserRegistrationRequestDto request = prepareRequest();

        //Action
        userService.createUser(request);

        //Assert
        verify(userRepository, Mockito.times(1)).create(userCaptor.capture());
        User createdUser = userCaptor.getValue();

        assertEquals(REQUEST_EMAIL, createdUser.getEmail());
        assertEquals(REQUEST_PASSWORD, createdUser.getPassword());
    }

    @Test
    public void testCreateExistedUser_expectIllegalArgumentException() {
        //Assign
        when(userRepository.findUserByEmail(eq(REQUEST_EMAIL))).thenReturn(Optional.of(UserDomainBuilder.persistedDefaultUser().build()));
        UserRegistrationRequestDto request = prepareRequest();

        //Action
        assertThrows(UserRegistrationException.class, () -> userService.createUser(request));
    }
}
