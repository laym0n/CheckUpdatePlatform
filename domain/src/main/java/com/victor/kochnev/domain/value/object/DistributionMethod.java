package com.victor.kochnev.domain.value.object;

import com.victor.kochnev.domain.enums.DistributionPlanType;

import java.math.BigDecimal;
import java.time.Duration;

/**
 * План распространения плагина
 */
public record DistributionMethod(DistributionPlanType type, Duration duration, BigDecimal cost) {
}
