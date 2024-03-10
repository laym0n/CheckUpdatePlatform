package com.victor.kochnev.integration.plugin.security.service;

import com.victor.kochnev.integration.plugin.security.entity.PluginSecurity;

public interface PluginSecurityService {
    PluginSecurity getAuthenticatedPlugin();
}
