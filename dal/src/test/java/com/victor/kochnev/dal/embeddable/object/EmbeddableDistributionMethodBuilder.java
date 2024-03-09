package com.victor.kochnev.dal.embeddable.object;

import com.victor.kochnev.domain.enums.DistributionPlanType;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class EmbeddableDistributionMethodBuilder {
    public static final Duration DEFAULT_DURATION = Duration.of(10, ChronoUnit.DAYS);
    public static final BigDecimal DEFAULT_COST = BigDecimal.TEN;

    private EmbeddableDistributionMethodBuilder() {

    }

    public static EmbeddableDistributionMethod.EmbeddableDistributionMethodBuilder defaultPurchaseDistribution() {
        return EmbeddableDistributionMethod.builder()
                .cost(DEFAULT_COST)
                .duration(null)
                .type(DistributionPlanType.PURCHASE);
    }

    public static EmbeddableDistributionMethod.EmbeddableDistributionMethodBuilder defaultSubscribeDistribution() {
        return EmbeddableDistributionMethod.builder()
                .cost(DEFAULT_COST)
                .duration(DEFAULT_DURATION)
                .type(DistributionPlanType.SUBSCRIBE);
    }
}
