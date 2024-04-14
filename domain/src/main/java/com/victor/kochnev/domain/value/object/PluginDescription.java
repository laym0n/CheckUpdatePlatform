package com.victor.kochnev.domain.value.object;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
    private List<DistributionMethod> distributionMethods;
}
