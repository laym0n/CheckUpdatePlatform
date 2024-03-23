package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.CreateTaskRequestBody;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;

@Mapper(componentModel = "spring",
        imports = Collections.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestTaskRequestMapper {
    CreateTaskRequestDto mapToCoreRequest(CreateTaskRequestBody requestBody);
}
