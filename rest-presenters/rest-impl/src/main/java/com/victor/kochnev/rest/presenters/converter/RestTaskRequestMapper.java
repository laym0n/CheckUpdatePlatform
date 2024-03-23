package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.CreateTaskRequestBody;
import com.victor.kochnev.api.dto.MakeDecisionRequestBody;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;
import com.victor.kochnev.core.dto.request.MakeDecisionRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;

@Mapper(componentModel = "spring",
        imports = Collections.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestTaskRequestMapper {
    CreateTaskRequestDto mapToCoreRequest(CreateTaskRequestBody requestBody);

    @Mapping(target = "taskId", ignore = true)
    MakeDecisionRequestDto mapToCoreRequest(MakeDecisionRequestBody requestBody);
}
