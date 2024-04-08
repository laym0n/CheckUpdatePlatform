package com.victor.kochnev.core.dto.domain.entity;

import com.victor.kochnev.domain.value.object.DistributionMethod;
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
public class PluginUsageDomainDto {
    private UUID id;
    private DistributionMethod distributionMethod;
    private ZonedDateTime expiredDate;
    private PluginDomainDto plugin;
}
