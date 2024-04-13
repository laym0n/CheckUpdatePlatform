package com.victor.kochnev.core.service.authorization;

import com.victor.kochnev.core.BaseCoreUnitTest;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.core.security.service.user.SecurityUserService;
import com.victor.kochnev.domain.entity.PluginUsage;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.entity.builder.PluginUsageDomainBuilder;
import com.victor.kochnev.domain.entity.builder.UserDomainBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthorizationServiceImplUnitTest extends BaseCoreUnitTest {
    @InjectMocks
    AuthorizationServiceImpl pluginUsageService;
    @Mock
    SecurityUserService securityUserService;

    @Test
    void testFindLastPluginUsageForUser_ThrowExceptionIfNotFind() {
        //Assign
        UUID pluginId = UUID.randomUUID();
        UserSecurity authenticatedUser = prepareSecurityUser();
        when(securityUserService.getCurrentUser()).thenReturn(authenticatedUser);
        when(pluginUsageRepository.findPluginUsageWithExpiredDateAfterOrNull(any(), any(), any())).thenReturn(Collections.emptyList());

        //Action
        boolean hasAccess = pluginUsageService.verifyCurrentUserCanUsePlugin(pluginId);

        //Assert
        assertFalse(hasAccess);
    }

    @Test
    void testFindLastPluginUsageForUser_SuccessExecution() {
        //Assign
        UUID pluginId = UUID.randomUUID();
        PluginUsage expectedPluginUsage = PluginUsageDomainBuilder.persistedDefaultUser().build();
        UserSecurity authenticatedUser = prepareSecurityUser();
        when(securityUserService.getCurrentUser()).thenReturn(authenticatedUser);
        when(pluginUsageRepository.findPluginUsageWithExpiredDateAfterOrNull(any(), any(), any()))
                .thenReturn(List.of(expectedPluginUsage));

        //Action
        boolean hasAccess = pluginUsageService.verifyCurrentUserCanUsePlugin(pluginId);

        //Assert
        assertTrue(hasAccess);
    }

    private UserSecurity prepareSecurityUser() {
        User domainUser = UserDomainBuilder.persistedDefaultUser().build();
        return domainUserMapper.mapToSecurityUser(domainUser);
    }
}
