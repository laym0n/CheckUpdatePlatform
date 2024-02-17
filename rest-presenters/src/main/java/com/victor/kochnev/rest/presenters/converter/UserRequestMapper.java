package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.UserRegistrationRequestBody;
import com.victor.kochnev.core.dto.request.UserRegistrationRequestDto;
import org.mapstruct.Mapper;

import java.util.Collections;

@Mapper(componentModel = "spring", imports = Collections.class)
public interface UserRequestMapper {
    UserRegistrationRequestDto mapToUserRegistrationRequestDto(UserRegistrationRequestBody requestBody);
}
