package com.victor.kochnev.domain.value.object;

import com.victor.kochnev.domain.enums.DistributionPlanType;

import java.math.BigDecimal;
import java.time.Duration;

/**
 * План распространения плагина
 *
 * @param type     тип плана распростронения
 * @param duration длительность действия плана распростронения
 * @param cost     стоимость плана распростронения
 */
public record DistributionMethod(DistributionPlanType type, Duration duration, BigDecimal cost) {
}
