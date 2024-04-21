package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.dal.GetPluginUsagesDalResponseDto;
import com.victor.kochnev.core.dto.domain.entity.PluginUsageDto;
import com.victor.kochnev.core.dto.request.CreatePluginUsageRequestDto;
import com.victor.kochnev.core.dto.response.GetPluginUsagesResponseDto;
import com.victor.kochnev.domain.entity.PluginUsage;
import org.mapstruct.*;

import java.time.ZonedDateTime;

@Mapper(uses = DomainPluginMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        builder = @Builder(disableBuilder = true),
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainPluginUsageMapper {

    PluginUsageDto mapToDto(PluginUsage pluginUsage);

    @BlankEntityMapping
    @Mapping(target = "plugin", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "expiredDate", ignore = true)
    PluginUsage mapToDomain(CreatePluginUsageRequestDto requestDto);

    GetPluginUsagesResponseDto mapToDto(GetPluginUsagesDalResponseDto dalResponseDto);

    @AfterMapping
    default void performExpiredDateMapping(final CreatePluginUsageRequestDto requestDto, @MappingTarget final PluginUsage pluginUsage) {
        pluginUsage.setExpiredDate(pluginUsage.getDistributionMethod().getExpiredDate(ZonedDateTime.now()));
    }
}
