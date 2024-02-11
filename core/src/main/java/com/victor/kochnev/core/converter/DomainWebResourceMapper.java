package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.domain.entity.WebResourceDto;
import com.victor.kochnev.core.dto.domain.value.object.ObserveSettingsDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.enums.WebResourceStatus;
import com.victor.kochnev.domain.value.object.ObserveSettings;
import org.mapstruct.*;

@Mapper(imports = {WebResourceStatus.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainWebResourceMapper {
    @BlankEntityMapping
    @Mapping(target = "plugin", ignore = true)
    @Mapping(target = "webResourceObservingCollection", ignore = true)
    @Mapping(target = "status", expression = "java(WebResourceStatus.NOT_OBSERVED)")
    WebResource mapToEntity(WebResourcePluginDto webResourcePluginDto);

    WebResourceDto mapToDto(WebResource webResource);

    @BlankEntityMapping
    @Mapping(target = "plugin", ignore = true)
    @Mapping(target = "webResourceObservingCollection", ignore = true)
    void update(@MappingTarget WebResource webResource, WebResourcePluginDto webResourcePluginDto);

    ObserveSettings mapToDomain(ObserveSettingsDto observeSettingsDto);
}
