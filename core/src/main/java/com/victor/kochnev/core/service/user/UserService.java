package com.victor.kochnev.core.service.user;

import com.victor.kochnev.core.dto.domain.UserDto;
import com.victor.kochnev.core.dto.request.UserRegistrationRequestDto;
import com.victor.kochnev.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(UserRegistrationRequestDto request);

    UserDto findUserByEmail(String email);

    List<User> findAllObserversOfWebResource(UUID pluginId, String webResourceName);
}
