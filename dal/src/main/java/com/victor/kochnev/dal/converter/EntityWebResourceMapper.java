package com.victor.kochnev.dal.converter;

import com.victor.kochnev.dal.entity.WebResourceEntity;
import com.victor.kochnev.domain.entity.WebResource;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = EntityPluginMapper.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntityWebResourceMapper {
    WebResource mapToDomain(WebResourceEntity webResource);

    WebResourceEntity mapToEntity(WebResource webResource);

    @Mapping(target = "plugin", ignore = true)
    WebResourceEntity update(@MappingTarget WebResourceEntity webResourceEntity, WebResource webResource);
}
