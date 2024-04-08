package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.CreatePluginUsageRequest;
import com.victor.kochnev.api.dto.PluginUsageDto;
import com.victor.kochnev.core.dto.domain.entity.PluginUsageDomainDto;
import com.victor.kochnev.core.dto.request.CreatePluginUsageRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(uses = RestPluginDtoMapper.class,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestPluginUsageMapper {

    PluginUsageDto mapToRestDto(PluginUsageDomainDto pluginUsageDomainDto);

    CreatePluginUsageRequestDto mapToCoreRequest(CreatePluginUsageRequest request);
}
