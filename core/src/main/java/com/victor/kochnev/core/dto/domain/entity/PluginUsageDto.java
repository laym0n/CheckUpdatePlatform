package com.victor.kochnev.core.dto.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.core.dto.domain.value.object.DistributionMethodDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PluginUsageDto {
    @JsonProperty("id")
    @NotNull
    private UUID id;
    @JsonProperty("distributionMethod")
    @NotNull
    private DistributionMethodDto distributionMethod;
    @JsonProperty("expiredDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm Z")
    @NotNull
    private ZonedDateTime expiredDate;
    @JsonProperty("plugin")
    @NotNull
    private PluginDto plugin;
}
