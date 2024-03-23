package com.victor.kochnev.dal.converter;

import com.victor.kochnev.core.converter.BlankEntityMapping;
import com.victor.kochnev.dal.entity.TaskEntity;
import com.victor.kochnev.domain.entity.Task;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = EntityUserMapper.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntityTaskMapper {
    Task mapToDomain(TaskEntity task);

    @Mapping(target = "plugin", ignore = true)
    TaskEntity mapToEntity(Task task);

    @BlankEntityMapping
    @Mapping(target = "plugin", ignore = true)
    void update(@MappingTarget TaskEntity dbTaskEntity, TaskEntity updatedTask);
}
