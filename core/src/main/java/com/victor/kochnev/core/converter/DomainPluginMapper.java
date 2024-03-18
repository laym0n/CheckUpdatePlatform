package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.domain.entity.PluginDto;
import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.security.entity.PluginAuthority;
import com.victor.kochnev.core.security.entity.PluginSecurity;
import com.victor.kochnev.domain.entity.Plugin;
import org.mapstruct.*;

import java.util.List;

@Mapper(imports = {PluginAuthority.class, List.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainPluginMapper {

    PluginDto mapToDto(Plugin plugin);

    @Mapping(target = "authorities", expression = "java(List.of(PluginAuthority.PLUGIN))")
    PluginSecurity mapToSecurity(Plugin plugin);

    @BlankEntityMapping
    @Mapping(target = "accessToken", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "ownerUser", ignore = true)
    Plugin mapToDomain(AddPluginRequestDto requestDto);
}
