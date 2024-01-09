package com.victor.kochnev.core.facade.user;

import com.victor.kochnev.core.dto.UserRegistrationRequestDto;
import com.victor.kochnev.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
    private final UserService userService;

    @Override
    public void registerUser(UserRegistrationRequestDto request) {
        userService.createUser(request);
    }
}
