package com.victor.kochnev.domain.value.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.domain.enums.DistributionPlanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * План распространения плагина
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class DistributionMethod {
    @JsonProperty("type")
    private DistributionPlanType type;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("duration")
    private Duration duration;
    @JsonProperty("cost")
    private BigDecimal cost;

    public ZonedDateTime getExpiredDate(ZonedDateTime from) {
        return type == DistributionPlanType.PURCHASE ? null : from.plus(duration);
    }
}
