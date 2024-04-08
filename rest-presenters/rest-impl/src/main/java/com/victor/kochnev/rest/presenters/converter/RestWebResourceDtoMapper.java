package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.WebResourceDto;
import com.victor.kochnev.core.dto.domain.entity.WebResourceDomainDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestWebResourceDtoMapper {
    WebResourceDto mapToRestDto(WebResourceDomainDto webResourceDomainDto);
}
