package com.victor.kochnev.integration.plugin.converter;

import com.victor.kochnev.core.dto.plugin.CanObserveResponseDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.integration.plugin.api.dto.CanObserveResponse;
import com.victor.kochnev.integration.plugin.api.dto.WebResourceDto;
import com.victor.kochnev.integration.plugin.security.entity.PluginAuthority;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(imports = {PluginAuthority.class, List.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PluginResponseMapper {
    CanObserveResponseDto mapToCore(CanObserveResponse response);

    WebResourcePluginDto mapToCore(WebResourceDto webResourceDto);
}
