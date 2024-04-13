package com.victor.kochnev.domain.value.object;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("specificDescription")
    private PluginSpecificDescription specificDescription;
    /**
     * Путь до логотипа плагина
     */
    @JsonProperty("logoPath")
    private String logoPath;
    /**
     * Способы распространения плагина
     */
    @JsonProperty("distributionMethods")
    private List<DistributionMethod> distributionMethods;
}
