package com.victor.kochnev.core.facade.webresourceobserving;

import com.victor.kochnev.core.base.BaseCoreUnitTest;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequest;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequestBuilder;
import com.victor.kochnev.core.exception.PluginUsageNotPermittedException;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.service.pluginusage.PluginUsageService;
import com.victor.kochnev.domain.entity.builder.PluginUsageDomainBuilder;
import com.victor.kochnev.domain.value.object.DistributionMethodBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class WebResourceObservingFacadeTest extends BaseCoreUnitTest {
    @InjectMocks
    WebResourceObservingFacadeImpl webResourceObservingFacade;
    @Mock
    PluginUsageService pluginUsageService;

    @Test
    void testThrowPluginUsageNotPermittedException_WhenUserNotUsePlugin() {
        //Assign
        AddWebResourceForObservingRequest request = AddWebResourceForObservingRequestBuilder.defaultBuilder().build();
        when(pluginUsageService.findLastPluginUsageForUser(request.getPluginId(), request.getUserId()))
                .thenThrow(ResourceNotFoundException.class);

        //Action
        assertThrows(PluginUsageNotPermittedException.class, () -> webResourceObservingFacade.addWebResourceForObserving(request));
    }

    @Test
    void testThrowPluginUsageNotPermittedException_WhenSubscribeExpired() {
        //Assign
        AddWebResourceForObservingRequest request = AddWebResourceForObservingRequestBuilder.defaultBuilder().build();
        when(pluginUsageService.findLastPluginUsageForUser(request.getPluginId(), request.getUserId()))
                .thenReturn(PluginUsageDomainBuilder.persistedDefaultUser()
                        .createDate(ZonedDateTime.now().minusDays(2))
                        .distributionMethod(DistributionMethodBuilder.subscribeDistribution(Duration.of(1, ChronoUnit.SECONDS))).build());

        //Action
        assertThrows(PluginUsageNotPermittedException.class, () -> webResourceObservingFacade.addWebResourceForObserving(request));
    }
}
