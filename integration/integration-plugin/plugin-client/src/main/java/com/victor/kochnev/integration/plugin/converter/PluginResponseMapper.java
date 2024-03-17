package com.victor.kochnev.integration.plugin.converter;

import com.victor.kochnev.core.dto.plugin.CanObserveResponseDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.integration.plugin.api.dto.CanObserveResponse;
import com.victor.kochnev.integration.plugin.api.dto.WebResourceDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PluginResponseMapper {
    CanObserveResponseDto mapToCore(CanObserveResponse response);

    WebResourcePluginDto mapToCore(WebResourceDto webResourceDto);
}
