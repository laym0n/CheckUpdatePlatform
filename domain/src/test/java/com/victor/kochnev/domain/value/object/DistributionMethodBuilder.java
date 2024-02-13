package com.victor.kochnev.domain.value.object;

import com.victor.kochnev.domain.enums.DistributionPlanType;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DistributionMethodBuilder {
    private DistributionMethodBuilder() {}
    public static final Duration DEFAULT_DURATION = Duration.of(10, ChronoUnit.DAYS);

    public static DistributionMethod defaultPurchaseDistribution() {
        return new DistributionMethod(DistributionPlanType.PURCHASE, null, BigDecimal.TEN);
    }

    public static DistributionMethod defaultSubscribeDistribution() {
        return new DistributionMethod(DistributionPlanType.SUBSCRIBE, DEFAULT_DURATION, BigDecimal.TEN);
    }

    public static DistributionMethod subscribeDistribution(Duration duration) {
        return new DistributionMethod(DistributionPlanType.SUBSCRIBE, duration, BigDecimal.TEN);
    }
}
