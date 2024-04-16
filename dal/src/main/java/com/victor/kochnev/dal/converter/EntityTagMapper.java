package com.victor.kochnev.dal.converter;

import com.victor.kochnev.core.converter.BlankEntityMapping;
import com.victor.kochnev.dal.entity.TagEntity;
import com.victor.kochnev.domain.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {EntityUserMapper.class, EntityPluginMapper.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntityTagMapper {
    Tag mapToDomain(TagEntity tag);

    @BlankEntityMapping
    TagEntity mapToEntity(Tag task);
}
