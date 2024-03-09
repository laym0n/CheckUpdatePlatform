package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.core.dto.domain.entity.UserDto;
import com.victor.kochnev.domain.enums.UserRole;
import com.victor.kochnev.rest.presenters.security.entity.UserAuthoritySecurity;
import com.victor.kochnev.rest.presenters.security.entity.UserSecurity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;

@Mapper(componentModel = "spring",
        imports = Collections.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserDtoMapper {

    @Mapping(target = "authorities", source = "rolesCollection")
    UserSecurity mapToSecurityUser(UserDto userDto);

    @Mapping(target = "authority", expression = "java(\"ROLE_\" + userRole.name())")
    UserAuthoritySecurity mapToGrantedAuthority(UserRole userRole);
}
