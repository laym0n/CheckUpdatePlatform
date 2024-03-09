package com.victor.kochnev.core.facade.webresourceobserving;

import com.victor.kochnev.core.converter.DomainWebResourceObservingMapper;
import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.plugin.CanObserveResponseDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequest;
import com.victor.kochnev.core.dto.request.StopWebResourceObservingRequest;
import com.victor.kochnev.core.exception.AccessNotPermittedException;
import com.victor.kochnev.core.exception.ResourceDescriptionParseException;
import com.victor.kochnev.core.integration.PluginClient;
import com.victor.kochnev.core.service.plugin.PluginService;
import com.victor.kochnev.core.service.pluginusage.PluginUsageService;
import com.victor.kochnev.core.service.webresource.WebResourceService;
import com.victor.kochnev.core.service.webresourceobserving.WebResourceObservingService;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import com.victor.kochnev.domain.enums.ObserveStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebResourceObservingFacadeImpl implements WebResourceObservingFacade {
    private final WebResourceService webResourceService;
    private final PluginService pluginService;
    private final PluginClient pluginClient;
    private final PluginUsageService pluginUsageService;
    private final WebResourceObservingService webResourceObservingService;
    private final DomainWebResourceObservingMapper observingMapper;

    private static Boolean checkIsNotObserved(Optional<WebResource> optionalWebResource) {
        return optionalWebResource
                .map(webResource -> ObserveStatus.NOT_OBSERVE == webResource.getStatus())
                .orElse(true);
    }

    @Override
    public WebResourceObservingDto addWebResourceForObserving(AddWebResourceForObservingRequest request) {
        pluginUsageService.verifyUserCanUsePlugin(request.getPluginId(), request.getUserId());
        Plugin plugin = pluginService.findById(request.getPluginId());
        String baseUrl = plugin.getBaseUrl();
        CanObserveResponseDto response = pluginClient.canObserve(baseUrl, request.getResourceDescription());
        if (!response.isObservable()) {
            throw new ResourceDescriptionParseException(request.getResourceDescription(), request.getPluginId());
        }
        WebResourcePluginDto webResourcePluginDto = response.getWebResource();
        Optional<WebResource> optionalWebResource = webResourceService.findByNameAndPluginId(webResourcePluginDto.getName(), request.getPluginId());
        Boolean isNotObserved = checkIsNotObserved(optionalWebResource);
        if (isNotObserved) {
            webResourcePluginDto = pluginClient.addResourceForObserving(baseUrl, request.getResourceDescription());
        }
        return webResourceObservingService.addObservingCascade(webResourcePluginDto, request);
    }

    @Override
    public WebResourceObservingDto stopWebResourceObserving(StopWebResourceObservingRequest request) {
        WebResourceObserving observing = webResourceObservingService.getById(request.getWebResourceObservingId());
        if (!request.getUserId().equals(observing.getUser().getId())) {
            throw new AccessNotPermittedException("User with id " + request.getUserId() + " can not modify observing with id " + request.getWebResourceObservingId());
        }
        boolean isNeedChangeStatus = webResourceObservingService.stopObservingCascade(request.getWebResourceObservingId());
        if (isNeedChangeStatus) {
            Plugin plugin = observing.getWebResource().getPlugin();
            pluginClient.removeResourceFromObserve(plugin.getBaseUrl(), observing.getWebResource().getName());
        }
        observing = webResourceObservingService.getById(request.getWebResourceObservingId());
        return observingMapper.mapToDto(observing);
    }
}
