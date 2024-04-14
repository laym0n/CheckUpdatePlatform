package com.victor.kochnev.core.dto.domain.value.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
public class PluginDescriptionDto {
    @JsonProperty("specificDescription")
    private PluginSpecificDescriptionDto specificDescription;
    @JsonProperty("logoPath")
    private String logoPath;
    @JsonProperty("distributionMethods")
    private List<DistributionMethodDto> distributionMethods;
}
