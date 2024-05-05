package com.victor.kochnev.core.service.pluginusage;

import com.victor.kochnev.core.dto.domain.entity.PluginUsageDto;
import com.victor.kochnev.core.dto.request.CreatePluginUsageRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginUsagesRequestDto;
import com.victor.kochnev.core.dto.response.GetPluginUsagesResponseDto;

import java.util.UUID;

public interface PluginUsageService {

    PluginUsageDto create(CreatePluginUsageRequestDto requestDto, UUID userId);

    GetPluginUsagesResponseDto getByFiltersForUser(GetPluginUsagesRequestDto requestDto, UUID userId);

    void createOwningUsage(UUID userId, UUID pluginId);
}
