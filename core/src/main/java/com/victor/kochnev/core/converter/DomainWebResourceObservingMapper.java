package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import org.mapstruct.*;

@Mapper(uses = DomainWebResourceMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainWebResourceObservingMapper {

    @Mapping(target = "webResourceDto", source = "webResource")
    WebResourceObservingDto mapToDto(WebResourceObserving webResourceObserving);
}
