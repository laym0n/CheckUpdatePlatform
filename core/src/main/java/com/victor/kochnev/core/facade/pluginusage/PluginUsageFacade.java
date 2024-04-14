package com.victor.kochnev.core.facade.pluginusage;


import com.victor.kochnev.core.dto.domain.entity.PluginUsageDto;
import com.victor.kochnev.core.dto.request.CreatePluginUsageRequestDto;

public interface PluginUsageFacade {

    PluginUsageDto create(CreatePluginUsageRequestDto requestDto);
}
