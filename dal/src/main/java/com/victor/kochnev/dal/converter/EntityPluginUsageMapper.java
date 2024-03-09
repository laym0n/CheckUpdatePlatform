package com.victor.kochnev.dal.converter;

import com.victor.kochnev.dal.entity.PluginUsageEntity;
import com.victor.kochnev.domain.entity.PluginUsage;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {EntityUserMapper.class, EntityPluginMapper.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntityPluginUsageMapper {
    PluginUsage mapToDomain(PluginUsageEntity pluginUsageEntity);

    PluginUsageEntity mapToEntity(PluginUsage pluginUsage);
}
