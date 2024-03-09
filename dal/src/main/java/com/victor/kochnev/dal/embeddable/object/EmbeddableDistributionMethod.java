package com.victor.kochnev.dal.embeddable.object;

import com.victor.kochnev.domain.enums.DistributionPlanType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddableDistributionMethod {
    @Column(name = "distribution_type")
    @Enumerated(EnumType.STRING)
    private DistributionPlanType type;
    @Column(name = "distribution_duration")
    private Duration duration;
    @Column(name = "distribution_cost")
    private BigDecimal cost;
}
