package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.domain.entity.PluginUsageDto;
import com.victor.kochnev.core.dto.request.CreatePluginUsageRequestDto;
import com.victor.kochnev.domain.entity.PluginUsage;
import org.mapstruct.*;

import java.time.ZonedDateTime;

@Mapper(uses = DomainPluginMapper.class,
        imports = ZonedDateTime.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainPluginUsageMapper {

    PluginUsageDto mapToDto(PluginUsage pluginUsage);

    @BlankEntityMapping
    @Mapping(target = "plugin", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "expiredDate", expression = "java(requestDto.getDistributionMethod().getExpiredDate(ZonedDateTime.now()))")
    PluginUsage mapToDomain(CreatePluginUsageRequestDto requestDto);
}
