package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.domain.entity.TaskDomainDto;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;
import com.victor.kochnev.core.dto.request.MakeDecisionRequestDto;
import com.victor.kochnev.domain.entity.Task;
import org.mapstruct.*;

@Mapper(uses = DomainPluginMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainTaskMapper {

    TaskDomainDto mapToDto(Task task);

    @BlankEntityMapping
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "plugin", ignore = true)
    @Mapping(target = "decision", ignore = true)
    @Mapping(target = "comment", ignore = true)
    Task mapToDomain(CreateTaskRequestDto requestDto);

    @BlankEntityMapping
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "plugin", ignore = true)
    void update(@MappingTarget Task task, MakeDecisionRequestDto requestDto);
}
