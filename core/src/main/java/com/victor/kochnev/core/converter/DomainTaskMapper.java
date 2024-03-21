package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.domain.entity.TaskDto;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;
import com.victor.kochnev.domain.entity.Task;
import org.mapstruct.*;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainTaskMapper {

    TaskDto mapToDto(Task task);

    @BlankEntityMapping
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "plugin", ignore = true)
    Task mapToDomain(CreateTaskRequestDto requestDto);
}
