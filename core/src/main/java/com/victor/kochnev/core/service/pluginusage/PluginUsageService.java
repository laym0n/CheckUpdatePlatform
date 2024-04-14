package com.victor.kochnev.core.service.pluginusage;

import com.victor.kochnev.core.dto.domain.entity.PluginUsageDto;
import com.victor.kochnev.core.dto.request.CreatePluginUsageRequestDto;

import java.util.UUID;

public interface PluginUsageService {

    PluginUsageDto create(CreatePluginUsageRequestDto requestDto, UUID userId);
}
