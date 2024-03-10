package com.victor.kochnev.integration.plugin.converter;

import com.victor.kochnev.core.dto.request.SendNotificationRequestDto;
import com.victor.kochnev.integration.plugin.api.dto.NotificationCreateRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(uses = PluginDtoMapper.class,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PluginRequestMapper {
    SendNotificationRequestDto mapToCore(NotificationCreateRequestBody requestBody);
}
