package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.AddPluginResponseBody;
import com.victor.kochnev.api.dto.Plugin;
import com.victor.kochnev.core.dto.domain.entity.PluginDto;
import com.victor.kochnev.core.dto.response.AddPluginResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestPluginDtoMapper {
    AddPluginResponseBody mapToRestDto(AddPluginResponseDto addPluginResponseDto);

    Plugin mapToRestDto(PluginDto pluginDto);
}
