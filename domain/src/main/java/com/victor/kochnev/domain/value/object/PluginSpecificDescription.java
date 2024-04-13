package com.victor.kochnev.domain.value.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Подробное описание плагина
 */
@Data
@NoArgsConstructor
@SuperBuilder
public class PluginSpecificDescription {
    /**
     * Описание плагина
     */
    @JsonProperty("description")
    private String description;
    /**
     * Пути до файлов изображений плагина
     */
    @JsonProperty("imagePaths")
    private List<String> imagePaths;
    /**
     * Теги плагина
     */
    @JsonProperty("tags")
    private List<String> tags;
}
