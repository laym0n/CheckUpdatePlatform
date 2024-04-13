package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDomainDto;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(uses = DomainWebResourceMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainWebResourceObservingMapper {

    WebResourceObservingDomainDto mapToDto(WebResourceObserving webResourceObserving);
}
