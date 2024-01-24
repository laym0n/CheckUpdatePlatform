package com.victor.kochnev.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Notification extends BaseEntity {
    /**
     * Сообщение оповещения
     */
    private String message;
    /**
     * Пользователь, которому адресовано оповещение
     */
    private User user;
}
