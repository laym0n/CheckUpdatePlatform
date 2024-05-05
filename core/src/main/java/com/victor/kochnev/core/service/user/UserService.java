package com.victor.kochnev.core.service.user;

import com.victor.kochnev.core.dto.request.UserRegistrationRequestDto;
import com.victor.kochnev.domain.entity.User;

public interface UserService {
    void createUser(UserRegistrationRequestDto request);

    User findUserByLogin(String login);
}
