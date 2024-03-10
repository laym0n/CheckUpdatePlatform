package com.victor.kochnev.domain.entity;

import com.victor.kochnev.domain.value.object.DistributionMethod;
import com.victor.kochnev.domain.value.object.PluginDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.List;

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
     * Пути до файлов изображений плагина
     */
    private List<String> imagePathsList;
    /**
     * Подробное описание плагина
     */
    private PluginDescription description;
    /**
     * Способы распространения плагина
     */
    private Collection<DistributionMethod> distributionMethodsCollection;
    /**
     * Владелец плагина
     */
    private User ownerUser;
}
