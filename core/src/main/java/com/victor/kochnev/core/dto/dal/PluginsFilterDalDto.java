package com.victor.kochnev.core.dto.dal;

import com.victor.kochnev.domain.enums.PluginStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PluginsFilterDalDto {
    private List<UUID> ids;
    private String name;
    private List<String> tags;
    private List<PluginStatus> statuses;
    private List<UUID> ownerIds;
    private UUID pluginUsageUserId;
}
