package com.victor.kochnev.core.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.core.dto.domain.value.object.DistributionMethodDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePluginUsageRequestDto {
    @JsonProperty("pluginId")
    @NotNull
    private UUID pluginId;
    @JsonProperty("distributionMethod")
    @NotNull
    private DistributionMethodDto distributionMethod;
}
