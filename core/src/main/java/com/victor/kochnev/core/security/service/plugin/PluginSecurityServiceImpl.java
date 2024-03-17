package com.victor.kochnev.core.security.service.plugin;

import com.victor.kochnev.core.converter.DomainPluginMapper;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.security.entity.PluginSecurity;
import com.victor.kochnev.core.service.plugin.PluginService;
import com.victor.kochnev.domain.entity.Plugin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PluginSecurityServiceImpl implements PluginSecurityService {
    private final PluginService pluginService;
    private final DomainPluginMapper pluginMapper;

    @Override
    public UserDetails loadUserByUsername(String pluginName) throws UsernameNotFoundException {
        Plugin pluginByName;
        try {
            pluginByName = pluginService.findByName(pluginName);
        } catch (ResourceNotFoundException ex) {
            throw new UsernameNotFoundException(ex.getMessage());
        }
        return pluginMapper.mapToSecurity(pluginByName);
    }

    @Override
    public PluginSecurity getAuthenticatedPlugin() {
        return (PluginSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
