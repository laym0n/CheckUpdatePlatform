package com.victor.kochnev.core.dto.domain.value.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.domain.enums.DistributionPlanType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class DistributionMethodDto {
    @JsonProperty("type")
    private DistributionPlanType type;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("duration")
    @Schema(example = "PT1H", type = "string")
    private Duration duration;
    @JsonProperty("cost")
    private BigDecimal cost;
}
