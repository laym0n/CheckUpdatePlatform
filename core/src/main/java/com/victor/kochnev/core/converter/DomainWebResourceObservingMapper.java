package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.dal.GetWebResourceObservingDalResponseDto;
import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.response.GetWebResouceObservingsResponseDto;
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

    WebResourceObservingDto mapToDto(WebResourceObserving webResourceObserving);

    GetWebResouceObservingsResponseDto mapToDto(GetWebResourceObservingDalResponseDto dalResponseDto);
}
