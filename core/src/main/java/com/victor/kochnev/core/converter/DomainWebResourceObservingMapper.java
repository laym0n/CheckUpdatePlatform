package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.domain.value.object.ObserveSettingsDto;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import com.victor.kochnev.domain.value.object.ObserveSettings;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainWebResourceObservingMapper {

    WebResourceObservingDto mapToDto(WebResourceObserving webResourceObserving);

    ObserveSettings mapToDomain(ObserveSettingsDto observeSettingsDto);
}
