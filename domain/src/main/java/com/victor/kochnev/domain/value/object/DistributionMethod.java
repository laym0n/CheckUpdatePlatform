package com.victor.kochnev.domain.value.object;

import com.victor.kochnev.domain.enums.DistributionPlanType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Duration;

/**
 * План распространения плагина
 */
@Data
@NoArgsConstructor
@SuperBuilder
public class DistributionMethod {
    private DistributionPlanType type;
    private Duration duration;
    private BigDecimal cost;
}
