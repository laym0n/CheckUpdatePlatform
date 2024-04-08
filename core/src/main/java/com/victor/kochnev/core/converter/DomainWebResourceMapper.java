package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.domain.entity.WebResourceDomainDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.enums.ObserveStatus;
import org.mapstruct.*;

@Mapper(imports = {ObserveStatus.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainWebResourceMapper {
    @BlankEntityMapping
    @Mapping(target = "plugin", ignore = true)
    @Mapping(target = "status", expression = "java(ObserveStatus.NOT_OBSERVE)")
    WebResource mapToEntity(WebResourcePluginDto webResourcePluginDto);

    WebResourceDomainDto mapToDto(WebResource webResource);

    @BlankEntityMapping
    @Mapping(target = "plugin", ignore = true)
    @Mapping(target = "status", ignore = true)
    void update(@MappingTarget WebResource webResource, WebResourcePluginDto webResourcePluginDto);
}
