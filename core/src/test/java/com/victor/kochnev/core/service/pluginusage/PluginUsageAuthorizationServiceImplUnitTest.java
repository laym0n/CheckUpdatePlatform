package com.victor.kochnev.core.service.pluginusage;

import com.victor.kochnev.core.BaseCoreUnitTest;
import com.victor.kochnev.domain.entity.PluginUsage;
import com.victor.kochnev.domain.entity.builder.PluginUsageDomainBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PluginUsageAuthorizationServiceImplUnitTest extends BaseCoreUnitTest {
    @InjectMocks
    PluginUsageAuthorizationServiceImpl pluginUsageService;

    @Test
    void testFindLastPluginUsageForUser_ThrowExceptionIfNotFind() {
        //Assign
        UUID userId = UUID.randomUUID();
        UUID pluginId = UUID.randomUUID();
        when(pluginUsageRepository.findPluginUsageWithExpiredDateAfterOrNull(any(), any(), any())).thenReturn(Collections.emptyList());

        //Action
        boolean hasAccess = pluginUsageService.verifyUserCanUsePlugin(pluginId, userId);

        //Assert
        assertFalse(hasAccess);
    }

    @Test
    void testFindLastPluginUsageForUser_SuccessExecution() {
        //Assign
        UUID userId = UUID.randomUUID();
        UUID pluginId = UUID.randomUUID();
        PluginUsage expectedPluginUsage = PluginUsageDomainBuilder.persistedDefaultUser().build();
        when(pluginUsageRepository.findPluginUsageWithExpiredDateAfterOrNull(any(), any(), any()))
                .thenReturn(List.of(expectedPluginUsage));

        //Action
        boolean hasAccess = pluginUsageService.verifyUserCanUsePlugin(pluginId, userId);

        //Assert
        assertTrue(hasAccess);
    }
}
