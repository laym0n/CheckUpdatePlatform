package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.core.dto.UserDto;
import com.victor.kochnev.rest.presenters.security.UserSecurity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;

@Mapper(componentModel = "spring", imports = Collections.class)
public interface UserDtoMapper {

    @Mapping(target = "authorities", expression = "java(Collections.emptyList())")
    UserSecurity mapToSecurityUser(UserDto userDto);
}
