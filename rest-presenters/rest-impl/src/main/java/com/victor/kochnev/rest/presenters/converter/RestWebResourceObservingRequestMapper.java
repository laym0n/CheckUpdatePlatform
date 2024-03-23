package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.WebResourceObservingAddRequest;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestWebResourceObservingRequestMapper {

    @Mapping(target = "userId", ignore = true)
    AddWebResourceForObservingRequestDto mapToCoreRequest(WebResourceObservingAddRequest requestBody);
}
