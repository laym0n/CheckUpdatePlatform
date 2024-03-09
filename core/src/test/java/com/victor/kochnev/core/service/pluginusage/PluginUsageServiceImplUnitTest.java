package com.victor.kochnev.core.service.pluginusage;

import com.victor.kochnev.core.BaseCoreUnitTest;
import com.victor.kochnev.core.exception.PluginUsageNotPermittedException;
import com.victor.kochnev.domain.entity.PluginUsage;
import com.victor.kochnev.domain.entity.builder.PluginUsageDomainBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PluginUsageServiceImplUnitTest extends BaseCoreUnitTest {
    @InjectMocks
    PluginUsageServiceImpl pluginUsageService;

    @Test
    void testFindLastPluginUsageForUser_ThrowExceptionIfNotFind() {
        //Assign
        UUID userId = UUID.randomUUID();
        UUID pluginId = UUID.randomUUID();
        when(pluginUsageRepository.findPluginUsageWithExpiredDateAfterOrNull(any(), any(), any())).thenReturn(Collections.emptyList());

        //Action
        assertThrows(PluginUsageNotPermittedException.class, () -> pluginUsageService.verifyUserCanUsePlugin(pluginId, userId));
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
        assertDoesNotThrow(() -> pluginUsageService.verifyUserCanUsePlugin(pluginId, userId));
    }
}
