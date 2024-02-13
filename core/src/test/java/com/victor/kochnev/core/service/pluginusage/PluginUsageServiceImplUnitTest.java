package com.victor.kochnev.core.service.pluginusage;

import com.victor.kochnev.core.base.BaseCoreUnitTest;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.domain.entity.PluginUsage;
import com.victor.kochnev.domain.entity.builder.PluginUsageDomainBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        when(pluginUsageRepository.findLastPluginUsage(any(), any())).thenReturn(Optional.empty());

        //Action
        assertThrows(ResourceNotFoundException.class, () -> pluginUsageService.findLastPluginUsageForUser(pluginId, userId));
    }

    @Test
    void testFindLastPluginUsageForUser_SuccessExecution() {
        //Assign
        UUID userId = UUID.randomUUID();
        UUID pluginId = UUID.randomUUID();
        PluginUsage expectedPluginUsage = PluginUsageDomainBuilder.persistedDefaultUser().build();
        when(pluginUsageRepository.findLastPluginUsage(any(), any()))
                .thenReturn(Optional.of(expectedPluginUsage));

        //Action
        PluginUsage actualPluginUsage = pluginUsageService.findLastPluginUsageForUser(pluginId, userId);

        //Assert
        assertEquals(expectedPluginUsage.getId(), actualPluginUsage.getId());
    }
}
