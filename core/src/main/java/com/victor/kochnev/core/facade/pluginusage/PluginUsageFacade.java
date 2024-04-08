package com.victor.kochnev.core.facade.pluginusage;


import com.victor.kochnev.core.dto.domain.entity.PluginUsageDomainDto;
import com.victor.kochnev.core.dto.request.CreatePluginUsageRequestDto;

public interface PluginUsageFacade {

    PluginUsageDomainDto create(CreatePluginUsageRequestDto requestDto);
}
