package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.WebResourceObservingAddRequestBody;
import com.victor.kochnev.api.dto.WebResourceObservingStopRequestBody;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequest;
import com.victor.kochnev.core.dto.request.StopWebResourceObservingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WebResourceObservingRequestMapper {

    @Mapping(target = "userId", ignore = true)
    AddWebResourceForObservingRequest mapToAddRequest(WebResourceObservingAddRequestBody requestBody);

    @Mapping(target = "userId", ignore = true)
    StopWebResourceObservingRequest mapToStopObserveRequest(WebResourceObservingStopRequestBody requestBody);
}
