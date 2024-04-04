package com.victor.kochnev.dal.converter;

import com.victor.kochnev.core.converter.BlankEntityMapping;
import com.victor.kochnev.dal.embeddable.object.EmbeddablePluginDescription;
import com.victor.kochnev.dal.entity.PluginEntity;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.value.object.PluginDescription;
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

    @Mapping(target = "tags", source = "tags.tags")
    PluginDescription mapToDomain(EmbeddablePluginDescription description);

    @Mapping(target = "tags.tags", source = "tags")
    EmbeddablePluginDescription mapToDal(PluginDescription description);
}
