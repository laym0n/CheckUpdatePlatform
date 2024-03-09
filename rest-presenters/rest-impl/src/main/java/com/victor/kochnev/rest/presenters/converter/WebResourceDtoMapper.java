package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.WebResource;
import com.victor.kochnev.core.dto.domain.entity.WebResourceDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WebResourceDtoMapper {
    WebResource mapToRestDto(WebResourceDto webResourceDto);
}
