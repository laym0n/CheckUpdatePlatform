package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.dal.GetPluginUsagesDalRequestDto;
import com.victor.kochnev.core.dto.dal.GetPluginsDalRequestDto;
import com.victor.kochnev.core.dto.dal.GetTasksDalRequestDto;
import com.victor.kochnev.core.dto.dal.GetWebResourceObservingDalRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginUsagesRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginsRequestDto;
import com.victor.kochnev.core.dto.request.GetTasksRequestDto;
import com.victor.kochnev.core.dto.request.GetWebResourceObservingsRequestDto;
import org.mapstruct.*;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RequestDtoMapper {

    @Mapping(target = "filters.userId", ignore = true)
    GetPluginsDalRequestDto mapToDal(GetPluginsRequestDto request);

    @Mapping(target = "filters.userIds", ignore = true)
    GetWebResourceObservingDalRequestDto mapToDal(GetWebResourceObservingsRequestDto request);

    @Mapping(target = "filters.userIds", ignore = true)
    GetPluginUsagesDalRequestDto mapToDal(GetPluginUsagesRequestDto requestDto);

    GetTasksDalRequestDto mapToDal(GetTasksRequestDto requestDto);
}
