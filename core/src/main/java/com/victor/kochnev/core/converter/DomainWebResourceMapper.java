package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.domain.WebResourceDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.domain.entity.WebResource;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainWebResourceMapper {
    @BlankEntityMapping
    @Mapping(target = "plugin", ignore = true)
    @Mapping(target = "usersCollection", ignore = true)
    WebResource mapToEntity(WebResourcePluginDto webResourcePluginDto);

    WebResourceDto mapToDto(WebResource webResource);

    @BlankEntityMapping
    @Mapping(target = "plugin", ignore = true)
    @Mapping(target = "usersCollection", ignore = true)
    void update(@MappingTarget WebResource webResource, WebResourcePluginDto webResourcePluginDto);
}
