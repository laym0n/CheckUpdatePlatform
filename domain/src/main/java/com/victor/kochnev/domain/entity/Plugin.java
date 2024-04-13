package com.victor.kochnev.domain.entity;

import com.victor.kochnev.domain.enums.PluginStatus;
import com.victor.kochnev.domain.value.object.PluginDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Plugin extends BaseEntity {
    /**
     * Название плагина
     */
    private String name;
    /**
     * Префикс URL для взаимодействия с плагином
     */
    private String baseUrl;
    /**
     * Токен для доступа
     */
    private String accessToken;
    /**
     * Статус плагина
     */
    private PluginStatus status;
    /**
     * Описание плагина
     */
    private PluginDescription description;
    /**
     * Владелец плагина
     */
    private User ownerUser;
}
