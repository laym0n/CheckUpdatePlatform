package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.domain.entity.PluginDto;
import com.victor.kochnev.domain.entity.Plugin;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainPluginMapper {

    PluginDto mapToDto(Plugin plugin);
}
