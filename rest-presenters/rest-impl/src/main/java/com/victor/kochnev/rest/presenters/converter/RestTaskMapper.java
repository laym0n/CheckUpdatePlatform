package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.Task;
import com.victor.kochnev.core.dto.domain.entity.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(uses = {RestPluginDtoMapper.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestTaskMapper {
    Task mapToRestDto(TaskDto taskDto);
}
