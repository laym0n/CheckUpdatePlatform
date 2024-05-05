package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.dal.GetPluginsDalResponseDto;
import com.victor.kochnev.core.dto.domain.entity.PluginInfoDto;
import com.victor.kochnev.core.dto.domain.value.object.DistributionMethodDto;
import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.dto.request.UpdatePluginRequestDto;
import com.victor.kochnev.core.dto.response.AddPluginResponseDto;
import com.victor.kochnev.core.dto.response.GetPluginsResponseDto;
import com.victor.kochnev.core.security.entity.PluginAuthority;
import com.victor.kochnev.core.security.entity.PluginSecurity;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.value.object.DistributionMethod;
import org.mapstruct.*;

import java.util.List;

@Mapper(imports = {PluginAuthority.class, List.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainPluginMapper {

    @Mapping(target = "plugin", source = ".")
    @Mapping(target = "accessToken", ignore = true)
    AddPluginResponseDto mapToAddPluginResponseDto(Plugin plugin);

    @Mapping(target = "authorities", expression = "java(List.of(PluginAuthority.PLUGIN))")
    PluginSecurity mapToSecurity(Plugin plugin);

    @BlankEntityMapping
    @Mapping(target = "accessToken", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "ownerUser", ignore = true)
    Plugin mapToDomain(AddPluginRequestDto requestDto);

    GetPluginsResponseDto mapToDto(GetPluginsDalResponseDto dalResponseDto);

    DistributionMethod mapToDomain(DistributionMethodDto distributionMethod);

    @BlankEntityMapping
    @Mapping(target = "accessToken", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "ownerUser", ignore = true)
    void update(@MappingTarget Plugin plugin, UpdatePluginRequestDto requestDto);

    PluginInfoDto mapToDto(Plugin plugin);
}
