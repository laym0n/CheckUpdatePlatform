package com.victor.kochnev.dal.converter;

import com.victor.kochnev.dal.entity.PluginEntity;
import com.victor.kochnev.domain.entity.Plugin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = EntityUserMapper.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntityPluginMapper {
    Plugin mapToDomain(PluginEntity plugin);

    @Mapping(target = "webResourcesList", ignore = true)
    @Mapping(target = "pluginUsageEntityList", ignore = true)
    PluginEntity mapToEntity(Plugin plugin);
}
