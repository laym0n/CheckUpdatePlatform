package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.WebResourceObservingDto;
import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDomainDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = RestWebResourceDtoMapper.class,
        imports = RestWebResourceDtoMapper.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestWebResourceObservingDtoMapper {

    WebResourceObservingDto mapToRestDto(WebResourceObservingDomainDto webResourceObservingDomainDto);
}
