package com.victor.kochnev.domain.entity;

import com.victor.kochnev.domain.enums.WebResourceStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class WebResource extends BaseEntity {
    /**
     * Уникальное среди отслеживаемых ресурсов плагином название ресурса<br>
     * Получается от плагина
     */
    private String name;
    /**
     * Опциональное описание ресурса
     */
    private String description;
    /**
     * Статус веб ресурса
     */
    private WebResourceStatus status;
    /**
     * Плагин, отслеживающий ресурс
     */
    private Plugin plugin;
    private Collection<WebResourceObserving> webResourceObservingCollection;
}
