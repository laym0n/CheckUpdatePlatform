package com.victor.kochnev.core.facade.user;

import com.victor.kochnev.core.dto.UserRegistrationRequestDto;

public interface UserFacade {
    void registerUser(UserRegistrationRequestDto request);
}
