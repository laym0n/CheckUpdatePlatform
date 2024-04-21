package com.victor.kochnev.core.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.core.dto.domain.entity.PluginUsageDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPluginUsagesResponseDto {
    @JsonProperty("pluginUsages")
    @NotNull
    private List<PluginUsageDto> pluginUsages;
}
