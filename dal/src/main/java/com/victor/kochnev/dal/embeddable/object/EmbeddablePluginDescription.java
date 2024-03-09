package com.victor.kochnev.dal.embeddable.object;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Подробное описание плагина
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddablePluginDescription {
    @Column(name = "description")
    private String description;
}
