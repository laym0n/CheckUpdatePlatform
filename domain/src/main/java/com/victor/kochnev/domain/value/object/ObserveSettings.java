package com.victor.kochnev.domain.value.object;

/**
 * Настройки отслеживания веб ресурса
 * @param email email, на который должно приходить уведомление
 * @param telegram telegram, на который должно приходить уведомление
 */
public record ObserveSettings(String email, String telegram) {
}
