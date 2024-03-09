package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.domain.entity.UserDto;
import com.victor.kochnev.core.dto.request.UserRegistrationRequestDto;
import com.victor.kochnev.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainUserMapper {
    @BlankEntityMapping
    @Mapping(target = "enabled", expression = "java(true)")
    @Mapping(target = "telegramInfo", ignore = true)
    @Mapping(target = "rolesCollection", ignore = true)
    User mapToEntity(UserRegistrationRequestDto request);

    UserDto mapToUserDto(User user);
}
