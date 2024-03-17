package com.victor.kochnev.domain.entity;

import com.victor.kochnev.domain.enums.UpdateType;
import com.victor.kochnev.domain.value.object.PluginDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class PluginUpdateRequest extends BaseEntity {
    /**
     * Обновляемое описание плагина
     */
    private PluginDescription description;
    /**
     * Тип запроса обновления
     */
    private UpdateType type;
    /**
     * Плагин
     */
    private Plugin plugin;
}
