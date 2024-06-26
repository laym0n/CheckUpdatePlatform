package com.victor.kochnev.core.dto.dal;

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
public class PluginUsagesFilterDalDto {
    private List<UUID> pluginIds;
    private List<UUID> userIds;
}
