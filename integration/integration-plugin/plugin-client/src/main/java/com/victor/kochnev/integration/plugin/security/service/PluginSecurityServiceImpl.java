package com.victor.kochnev.integration.plugin.security.service;

import com.victor.kochnev.core.dto.domain.entity.PluginDto;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.facade.plugin.PluginFacade;
import com.victor.kochnev.integration.plugin.converter.PluginDtoMapper;
import com.victor.kochnev.integration.plugin.security.entity.PluginSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PluginSecurityServiceImpl implements UserDetailsService, PluginSecurityService {
    private final PluginFacade pluginFacade;
    private final PluginDtoMapper pluginDtoMapper;

    @Override
    public UserDetails loadUserByUsername(String pluginName) throws UsernameNotFoundException {
        PluginDto pluginByName;
        try {
            pluginByName = pluginFacade.findByName(pluginName);
        } catch (ResourceNotFoundException ex) {
            throw new UsernameNotFoundException(ex.getMessage());
        }
        return pluginDtoMapper.mapToSecurity(pluginByName);
    }

    @Override
    public PluginSecurity getAuthenticatedPlugin() {
        return (PluginSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
