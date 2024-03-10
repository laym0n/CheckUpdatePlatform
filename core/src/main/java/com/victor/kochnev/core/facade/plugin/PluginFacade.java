package com.victor.kochnev.core.facade.plugin;

import com.victor.kochnev.core.dto.domain.entity.PluginDto;

public interface PluginFacade {
    PluginDto findByName(String pluginName);
}
