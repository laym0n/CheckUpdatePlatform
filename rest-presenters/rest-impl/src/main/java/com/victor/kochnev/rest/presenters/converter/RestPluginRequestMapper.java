package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.AddPluginRequestBody;
import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestPluginRequestMapper {
    AddPluginRequestDto mapToCoreRequest(AddPluginRequestBody requestBody);
}
