package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.UserRegistrationRequestBody;
import com.victor.kochnev.core.dto.request.UserRegistrationRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;

@Mapper(componentModel = "spring",
        imports = Collections.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestUserRequestMapper {
    UserRegistrationRequestDto mapToCoreRequest(UserRegistrationRequestBody requestBody);
}
