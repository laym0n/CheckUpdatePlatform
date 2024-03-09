package com.victor.kochnev.core.service.user;

import com.victor.kochnev.core.dto.domain.entity.UserDto;
import com.victor.kochnev.core.dto.request.UserRegistrationRequestDto;

public interface UserService {
    void createUser(UserRegistrationRequestDto request);

    UserDto findUserByEmail(String email);
}
