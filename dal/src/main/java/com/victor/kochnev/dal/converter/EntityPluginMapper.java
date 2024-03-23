package com.victor.kochnev.dal.converter;

import com.victor.kochnev.core.converter.BlankEntityMapping;
import com.victor.kochnev.dal.entity.PluginEntity;
import com.victor.kochnev.domain.entity.Plugin;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = EntityUserMapper.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntityPluginMapper {
    Plugin mapToDomain(PluginEntity plugin);

    @Mapping(target = "webResourcesList", ignore = true)
    @Mapping(target = "pluginUsageEntityList", ignore = true)
    PluginEntity mapToEntity(Plugin plugin);

    @BlankEntityMapping
    @Mapping(target = "webResourcesList", ignore = true)
    @Mapping(target = "pluginUsageEntityList", ignore = true)
    @Mapping(target = "ownerUser", ignore = true)
    void update(@MappingTarget PluginEntity dbPluginEntity, PluginEntity updatedPluginEntity);
}
