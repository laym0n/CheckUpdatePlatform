package com.victor.kochnev.core.facade.plugin;

import com.victor.kochnev.core.converter.DomainPluginMapper;
import com.victor.kochnev.core.dto.domain.entity.PluginDto;
import com.victor.kochnev.core.service.plugin.PluginService;
import com.victor.kochnev.domain.entity.Plugin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PluginFacadeImpl implements PluginFacade {
    private final PluginService pluginService;
    private final DomainPluginMapper domainPluginMapper;

    @Override
    public PluginDto findByAccessToken(String accessToken) {
        Plugin plugin = pluginService.findByAccessToken(accessToken);
        return domainPluginMapper.mapToDto(plugin);
    }
}
