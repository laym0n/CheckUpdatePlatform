package com.victor.kochnev.core.facade.webresourceobserving;

import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequest;
import com.victor.kochnev.core.dto.request.RemoveWebResourceFromObservingRequest;
import com.victor.kochnev.core.exception.PluginUsageNotPermittedException;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.integration.PluginClient;
import com.victor.kochnev.core.service.plugin.PluginService;
import com.victor.kochnev.core.service.pluginusage.PluginUsageService;
import com.victor.kochnev.core.service.webresource.WebResourceService;
import com.victor.kochnev.core.service.webresourceobserving.WebResourceObservingService;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.entity.PluginUsage;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.enums.ObserveStatus;
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
        PluginUsage lastPluginUsage;
        try {
            lastPluginUsage = pluginUsageService.findLastPluginUsageForUser(request.getPluginId(), request.getUserId());
        } catch (ResourceNotFoundException e) {
            throw new PluginUsageNotPermittedException(request.getPluginId(), e);
        }
        if (!lastPluginUsage.canUse(ZonedDateTime.now())) {
            throw new PluginUsageNotPermittedException(request.getPluginId());
        }
        Plugin plugin = pluginService.findById(request.getPluginId());
        String baseUrl = plugin.getBaseUrl();
        WebResourcePluginDto webResourceDto = pluginClient.canObserve(baseUrl, request.getResourceDescription());
        WebResource webResource = webResourceService.updateOrCreate(plugin.getId(), webResourceDto);
        if (ObserveStatus.NOT_OBSERVE == webResource.getStatus()) {
            pluginClient.addResourceForObserving(baseUrl, request.getResourceDescription());
            webResourceService.setStatus(ObserveStatus.OBSERVE, webResource.getId());
        }
        return webResourceObservingService.updateOrCreate(webResource, request);
    }

    @Override
    public WebResourceObservingDto removeWebResourceFromObserving(RemoveWebResourceFromObservingRequest request) {
        WebResourceObservingDto webResourceObservingDto = webResourceObservingService.setStatusByUserIdAndWebResourceId(request.getUserId(), request.getWebResourceId(), ObserveStatus.NOT_OBSERVE);
        boolean isNeedChangeStatus = webResourceService.isNeedStopObserve(request.getWebResourceId());
        if (isNeedChangeStatus) {
            pluginClient.removeResourceFromObserve(webResourceObservingDto.getWebResourceDto().getName());
            webResourceService.setStatus(ObserveStatus.NOT_OBSERVE, request.getWebResourceId());
        }
        return webResourceObservingDto;
    }
}
