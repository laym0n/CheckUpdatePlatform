package com.victor.kochnev.domain.value.object;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Подробное описание плагина
 */
@Data
@NoArgsConstructor
@SuperBuilder
public class PluginDescription {
    private String description;
}
