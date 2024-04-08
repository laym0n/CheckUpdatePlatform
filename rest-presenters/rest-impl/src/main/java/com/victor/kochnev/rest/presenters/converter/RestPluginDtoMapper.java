package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.AddPluginResponse;
import com.victor.kochnev.api.dto.PluginDto;
import com.victor.kochnev.core.dto.domain.entity.PluginDomainDto;
import com.victor.kochnev.core.dto.response.AddPluginResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestPluginDtoMapper {
    AddPluginResponse mapToRestDto(AddPluginResponseDto addPluginResponseDto);

    PluginDto mapToRestDto(PluginDomainDto pluginDomainDto);
}
