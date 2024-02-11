package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.domain.entity.UserDto;
import com.victor.kochnev.core.dto.request.UserRegistrationRequestDto;
import com.victor.kochnev.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DomainUserMapper {
    User mapToEntity(UserRegistrationRequestDto request);

    UserDto mapToUserDto(User user);
}
