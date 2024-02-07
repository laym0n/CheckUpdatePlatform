package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.domain.WebResourceDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.domain.entity.WebResource;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainWebResourceMapper {
    WebResource mapToEntity(WebResourcePluginDto webResourcePluginDto);

    WebResourceDto mapToDto(WebResource webResource);

    void update(@MappingTarget WebResource webResource, WebResourcePluginDto webResourcePluginDto);
}
