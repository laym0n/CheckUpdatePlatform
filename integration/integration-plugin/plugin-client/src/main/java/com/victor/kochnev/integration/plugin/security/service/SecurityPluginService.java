package com.victor.kochnev.integration.plugin.security.service;

import com.victor.kochnev.core.dto.domain.entity.PluginDto;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.facade.plugin.PluginFacade;
import com.victor.kochnev.integration.plugin.converter.PluginDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityPluginService implements UserDetailsService {
    private final PluginFacade pluginFacade;
    private final PluginDtoMapper pluginDtoMapper;

    @Override
    public UserDetails loadUserByUsername(String pluginName) throws UsernameNotFoundException {
        PluginDto pluginByName;
        try {
            pluginByName = pluginFacade.findByAccessToken(pluginName);
        } catch (ResourceNotFoundException ex) {
            throw new UsernameNotFoundException(ex.getMessage());
        }
        return pluginDtoMapper.mapToSecurity(pluginByName);
    }
}
