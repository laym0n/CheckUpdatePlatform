package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.dal.GetPluginsDalRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginsRequestDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RequestDtoMapper {

    GetPluginsDalRequestDto mapToDal(GetPluginsRequestDto request);
}
