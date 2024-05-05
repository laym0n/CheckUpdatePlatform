package com.victor.kochnev.domain.entity;

import com.victor.kochnev.domain.enums.ObserveStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
     * Опциональный заголовок описания ресурса
     */
    private String descriptionHeader;
    /**
     * Опциональное описание ресурса
     */
    private String description;
    /**
     * Статус веб ресурса
     */
    private ObserveStatus status;
    /**
     * Плагин, отслеживающий ресурс
     */
    private Plugin plugin;
}
