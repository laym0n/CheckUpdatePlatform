package com.victor.kochnev.core.facade.plugin;

import com.victor.kochnev.core.converter.DomainPluginMapper;
import com.victor.kochnev.core.dto.domain.entity.PluginDto;
import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.core.security.service.user.SecurityUserService;
import com.victor.kochnev.core.service.plugin.PluginService;
import com.victor.kochnev.domain.entity.Plugin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PluginFacadeImpl implements PluginFacade {
    private final SecurityUserService securityUserService;
    private final PluginService pluginService;
    private final DomainPluginMapper domainPluginMapper;

    @Override
    public PluginDto addPlugin(AddPluginRequestDto requestDto) {
        UserSecurity user = securityUserService.getCurrentUser();
        UUID userId = user.getId();
        Plugin plugin = pluginService.create(userId, requestDto);
        return domainPluginMapper.mapToDto(plugin);
    }
}
