package com.victor.kochnev.domain.value.object;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Настройки отслеживания веб ресурса
 *
 * @needNotify необходимость уведомлять
 */
public record ObserveSettings(@JsonProperty("needNotify") boolean needNotify) {
}
