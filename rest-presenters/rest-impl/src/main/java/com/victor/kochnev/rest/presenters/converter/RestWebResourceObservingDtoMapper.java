package com.victor.kochnev.rest.presenters.converter;

import com.victor.kochnev.api.dto.WebResourceObserving;
import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = RestWebResourceDtoMapper.class,
        imports = RestWebResourceDtoMapper.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestWebResourceObservingDtoMapper {

    WebResourceObserving mapToRestDto(WebResourceObservingDto webResourceObservingDto);
}
