package com.victor.kochnev.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Feedback extends BaseEntity {
    /**
     * Комментарий
     */
    private String comment;
    /**
     * Оценка
     */
    private Integer rating;
    /**
     * Пользователь, оставивиший оценку
     */
    private User user;
    /**
     * Плагин, на котоорый оставили оценку
     */
    private Plugin plugin;
}
