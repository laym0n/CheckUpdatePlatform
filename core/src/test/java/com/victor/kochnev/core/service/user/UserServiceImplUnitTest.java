package com.victor.kochnev.core.service.user;

import com.victor.kochnev.core.BaseCoreUnitTest;
import com.victor.kochnev.core.dto.domain.entity.UserDto;
import com.victor.kochnev.core.dto.request.UserRegistrationRequestDto;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.exception.UserRegistrationException;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.entity.builder.UserDomainBuilder;
import com.victor.kochnev.domain.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplUnitTest extends BaseCoreUnitTest {
    private static final String REQUEST_EMAIL = "victor_k02@mail.ru";
    private static final String REQUEST_PASSWORD = "pass";
    @Spy
    private PasswordCoder passwordCoder = new PasswordCoderImpl();
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
    void testCreateNotExistedUser() {
        //Assign
        when(userRepository.findUserByEmail(REQUEST_EMAIL)).thenReturn(Optional.empty());
        UserRegistrationRequestDto request = prepareRequest();

        //Action
        userService.createUser(request);

        //Assert
        verify(userRepository, Mockito.times(1)).create(userCaptor.capture());
        User createdUser = userCaptor.getValue();

        assertEquals(REQUEST_EMAIL, createdUser.getEmail());
        String encodedPassword = passwordCoder.encode(REQUEST_PASSWORD);
        assertEquals(encodedPassword, createdUser.getPassword());
        assertEquals(List.of(UserRole.SIMPLE_USER), createdUser.getRolesCollection());
    }

    @Test
    void testCreateExistedUser_expectUserRegistrationException() {
        //Assign
        when(userRepository.findUserByEmail(REQUEST_EMAIL)).thenReturn(Optional.of(UserDomainBuilder.persistedDefaultUser().build()));
        UserRegistrationRequestDto request = prepareRequest();

        //Action
        assertThrows(UserRegistrationException.class, () -> userService.createUser(request));
    }

    @Test
    void testFindExistedUserByEmail() {
        //Assign
        User existedUser = UserDomainBuilder.persistedDefaultUser().build();
        when(userRepository.findUserByEmail(REQUEST_EMAIL)).thenReturn(Optional.of(existedUser));

        //Action
        UserDto userDto = userService.findUserByEmail(REQUEST_EMAIL);

        //Assert
        assertEquals(existedUser.getId(), userDto.getId());
        assertEquals(existedUser.getEmail(), userDto.getEmail());
        assertEquals(existedUser.getPassword(), userDto.getPassword());
        assertEquals(existedUser.isEnabled(), userDto.getEnabled());
    }

    @Test
    void testFindNotExistedUserByEmail_expectResourceNotFound() {
        //Assign
        when(userRepository.findUserByEmail(REQUEST_EMAIL)).thenReturn(Optional.empty());

        //Action
        assertThrows(ResourceNotFoundException.class, () -> userService.findUserByEmail(REQUEST_EMAIL));
    }
}