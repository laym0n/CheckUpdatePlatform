package com.victor.kochnev.integration.plugin.converter;

import com.victor.kochnev.core.dto.domain.entity.PluginDto;
import com.victor.kochnev.core.dto.plugin.NotificationPluginDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.integration.plugin.api.dto.NotificationDto;
import com.victor.kochnev.integration.plugin.api.dto.WebResourceDto;
import com.victor.kochnev.integration.plugin.security.entity.PluginAuthority;
import com.victor.kochnev.integration.plugin.security.entity.PluginSecurity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(imports = {PluginAuthority.class, List.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PluginDtoMapper {
    @Mapping(target = "authorities", expression = "java(List.of(PluginAuthority.PLUGIN))")
    PluginSecurity mapToSecurity(PluginDto pluginDto);
    WebResourcePluginDto mapToCore(WebResourceDto webResourceDto);
    NotificationPluginDto mapToCore(NotificationDto notificationDto);
}
