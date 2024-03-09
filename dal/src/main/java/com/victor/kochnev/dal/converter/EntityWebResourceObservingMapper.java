package com.victor.kochnev.dal.converter;

import com.victor.kochnev.dal.entity.WebResourceObservingEntity;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {EntityUserMapper.class, EntityWebResourceMapper.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntityWebResourceObservingMapper {
    WebResourceObserving mapToDomain(WebResourceObservingEntity webResourceObservingEntity);

    WebResourceObservingEntity mapToEntity(WebResourceObserving webResourceObserving);

    void update(@MappingTarget WebResourceObservingEntity observingEntity, WebResourceObserving webResourceObserving);
}
