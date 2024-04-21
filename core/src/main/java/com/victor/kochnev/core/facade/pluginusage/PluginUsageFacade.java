package com.victor.kochnev.core.facade.pluginusage;


import com.victor.kochnev.core.dto.domain.entity.PluginUsageDto;
import com.victor.kochnev.core.dto.request.CreatePluginUsageRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginUsagesRequestDto;
import com.victor.kochnev.core.dto.response.GetPluginUsagesResponseDto;

public interface PluginUsageFacade {

    PluginUsageDto create(CreatePluginUsageRequestDto requestDto);

    GetPluginUsagesResponseDto getByFilters(GetPluginUsagesRequestDto requestDto);
}
