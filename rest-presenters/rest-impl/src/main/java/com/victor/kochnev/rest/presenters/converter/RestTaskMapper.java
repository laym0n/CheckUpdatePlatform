package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.CreateTaskRequest;
import com.victor.kochnev.api.dto.MakeDecisionRequest;
import com.victor.kochnev.api.dto.TaskDto;
import com.victor.kochnev.core.dto.domain.entity.TaskDomainDto;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;
import com.victor.kochnev.core.dto.request.MakeDecisionRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(uses = {RestPluginDtoMapper.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestTaskMapper {
    TaskDto mapToRestDto(TaskDomainDto taskDomainDto);

    CreateTaskRequestDto mapToCoreRequest(CreateTaskRequest requestBody);

    @Mapping(target = "taskId", ignore = true)
    MakeDecisionRequestDto mapToCoreRequest(MakeDecisionRequest requestBody);
}
