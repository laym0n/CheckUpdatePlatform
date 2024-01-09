package com.victor.kochnev.core.service.user;

import com.victor.kochnev.core.dto.UserRegistrationRequestDto;

public interface UserService {
    void createUser(UserRegistrationRequestDto request);
}
