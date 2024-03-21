package com.victor.kochnev.core.dto.domain.value.object;

import com.victor.kochnev.domain.value.object.DistributionMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PluginDescriptionDto {
    private String description;
    private List<String> imagePaths;
    private List<DistributionMethod> distributionMethods;
}
