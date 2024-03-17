package com.victor.kochnev.core.security.service.plugin;

import com.victor.kochnev.core.security.entity.PluginSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface PluginSecurityService extends UserDetailsService {
    PluginSecurity getAuthenticatedPlugin();
}
