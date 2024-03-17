package com.victor.kochnev.integration.plugin.converter;

import com.victor.kochnev.core.dto.plugin.NotificationPluginDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.integration.plugin.api.dto.NotificationDto;
import com.victor.kochnev.integration.plugin.api.dto.WebResourceDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PluginDtoMapper {

    WebResourcePluginDto mapToCore(WebResourceDto webResourceDto);

    NotificationPluginDto mapToCore(NotificationDto notificationDto);
}
