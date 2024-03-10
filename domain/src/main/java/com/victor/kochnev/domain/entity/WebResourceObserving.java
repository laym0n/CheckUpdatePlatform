package com.victor.kochnev.domain.entity;

import com.victor.kochnev.domain.enums.ObserveStatus;
import com.victor.kochnev.domain.value.object.ObserveSettings;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Сущность отслеживания веб ресурса пользователем
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class WebResourceObserving extends BaseEntity {
    /**
     * Пользователь, отслеживающий веб ресурс
     */
    private User user;
    /**
     * Отслеживаемый веб ресурс
     */
    private WebResource webResource;
    /**
     * Статус отслеживания
     */
    private ObserveStatus status;
    /**
     * Настройки отслеживания веб ресурса
     */
    private ObserveSettings observeSettings;
}
