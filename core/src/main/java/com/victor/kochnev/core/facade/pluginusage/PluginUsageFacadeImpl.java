package com.victor.kochnev.core.facade.pluginusage;

import com.victor.kochnev.core.dto.domain.entity.PluginUsageDto;
import com.victor.kochnev.core.dto.request.CreatePluginUsageRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginUsagesRequestDto;
import com.victor.kochnev.core.dto.response.GetPluginUsagesResponseDto;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.core.security.service.user.SecurityUserService;
import com.victor.kochnev.core.service.pluginusage.PluginUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PluginUsageFacadeImpl implements PluginUsageFacade {
    private final SecurityUserService securityUserService;
    private final PluginUsageService pluginUsageService;

    @Override
    public PluginUsageDto create(CreatePluginUsageRequestDto requestDto) {
        UUID userId = securityUserService.getCurrentUser().getId();
        return pluginUsageService.create(requestDto, userId);
    }

    @Override
    public GetPluginUsagesResponseDto getByFilters(GetPluginUsagesRequestDto requestDto) {
        UserSecurity currentUser = securityUserService.getCurrentUser();
        return pluginUsageService.getByFiltersForUser(requestDto, currentUser.getId());
    }
}
