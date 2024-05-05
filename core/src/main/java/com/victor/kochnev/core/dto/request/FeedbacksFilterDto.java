package com.victor.kochnev.core.dto.request;

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
public class FeedbacksFilterDto {
    private List<UUID> ids;
    private List<UUID> pluginIds;
    private List<UUID> userIds;
    private List<UUID> excludedUserIds;
}
