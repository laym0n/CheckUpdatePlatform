package com.victor.kochnev.core.facade.webresourceobserving;

import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequest;
import com.victor.kochnev.core.exception.PluginUsageNotPermittedException;
import com.victor.kochnev.core.integration.PluginClient;
import com.victor.kochnev.core.service.plugin.PluginService;
import com.victor.kochnev.core.service.pluginusage.PluginUsageService;
import com.victor.kochnev.core.service.webresource.WebResourceService;
import com.victor.kochnev.core.service.webresourceobserving.WebResourceObservingService;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.entity.PluginUsage;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.enums.WebResourceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebResourceObservingFacadeImpl implements WebResourceObservingFacade {
    private final WebResourceService webResourceService;
    private final PluginService pluginService;
    private final PluginClient pluginClient;
    private final PluginUsageService pluginUsageService;
    private final WebResourceObservingService webResourceObservingService;

    @Override
    public WebResourceObservingDto addWebResourceForObserving(AddWebResourceForObservingRequest request) {
        PluginUsage lastPluginUsage = pluginUsageService.findLastPluginUsageForUser(request.getPluginId(), request.getUserId());
        if (!lastPluginUsage.canUse(ZonedDateTime.now())) {
            throw new PluginUsageNotPermittedException(request.getPluginId());
        }
        Plugin plugin = pluginService.findById(request.getPluginId());
        String baseUrl = plugin.getBaseUrl();
        WebResourcePluginDto webResourceDto = pluginClient.canObserve(baseUrl, request.getResourceDescription());
        WebResource webResource = webResourceService.updateOrCreate(plugin.getId(), webResourceDto);
        if (WebResourceStatus.NOT_OBSERVED == webResource.getStatus()) {
            pluginClient.addResourceForObserving(baseUrl, request.getResourceDescription());
            webResourceService.setStatus(WebResourceStatus.OBSERVED, webResource.getId());
        }
        WebResourceObservingDto webResourceObservingDto = webResourceObservingService.updateOrCreate(webResource, request);
        return webResourceObservingDto;
    }
}
