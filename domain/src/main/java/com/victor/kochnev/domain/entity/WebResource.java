package com.victor.kochnev.domain.entity;

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
     * Плагин, отслеживающий ресурс
     */
    private Plugin plugin;
    /**
     * Пользователи, отслеживающие ресурс
     */
    private Collection<User> usersCollection;
}
