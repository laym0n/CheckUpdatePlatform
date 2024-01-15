package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.core.dto.UserRegistrationRequestDto;
import com.victor.kochnev.rest.presenters.api.dto.UserRegistrationRequestBody;
import org.mapstruct.Mapper;

import java.util.Collections;

@Mapper(componentModel = "spring", imports = Collections.class)
public interface UserRequestMapper {
    UserRegistrationRequestDto mapToUserRegistrationRequestDto(UserRegistrationRequestBody requestBody);
}
