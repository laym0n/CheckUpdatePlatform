package com.victor.kochnev.domain.value.object;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

/**
 * Описание плагина
 */
@Data
@NoArgsConstructor
@SuperBuilder
public class PluginDescription {
    /**
     * Подробное описание плагина
     */
    private PluginSpecificDescription specificDescription;
    /**
     * Путь до логотипа плагина
     */
    private String logoPath;
    /**
     * Способы распространения плагина
     */
    private Collection<DistributionMethod> distributionMethods;
}
