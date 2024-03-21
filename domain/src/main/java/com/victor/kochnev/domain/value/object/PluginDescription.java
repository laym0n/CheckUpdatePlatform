package com.victor.kochnev.domain.value.object;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.List;

/**
 * Подробное описание плагина
 */
@Data
@NoArgsConstructor
@SuperBuilder
public class PluginDescription {
    /**
     * Описание плагина
     */
    private String description;
    /**
     * Пути до файлов изображений плагина
     */
    private List<String> imagePaths;
    /**
     * Способы распространения плагина
     */
    private Collection<DistributionMethod> distributionMethods;
}
