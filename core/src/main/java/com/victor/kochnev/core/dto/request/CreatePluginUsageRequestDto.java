package com.victor.kochnev.core.dto.request;

import com.victor.kochnev.domain.value.object.DistributionMethod;
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
    private UUID pluginId;
    private DistributionMethod distributionMethod;
}
