package com.victor.kochnev.core.facade.webresourceobserving;

import com.victor.kochnev.core.converter.DomainWebResourceObservingMapper;
import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.plugin.CanObserveResponseDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequestDto;
import com.victor.kochnev.core.dto.request.StopWebResourceObservingRequestDto;
import com.victor.kochnev.core.exception.ResourceDescriptionParseException;
import com.victor.kochnev.core.integration.PluginClient;
import com.victor.kochnev.core.service.plugin.PluginService;
import com.victor.kochnev.core.service.webresource.WebResourceService;
import com.victor.kochnev.core.service.webresourceobserving.WebResourceObservingService;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import com.victor.kochnev.domain.enums.ObserveStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebResourceObservingFacadeImpl implements WebResourceObservingFacade {
    private final WebResourceService webResourceService;
    private final PluginService pluginService;
    private final PluginClient pluginClient;
    private final WebResourceObservingService webResourceObservingService;
    private final DomainWebResourceObservingMapper observingMapper;

    @PreAuthorize("@authorizationService.verifyCurrentUserCanUsePlugin(#request.getPluginId())")
    @Override
    public WebResourceObservingDto addWebResourceForObserving(@P("request") AddWebResourceForObservingRequestDto request) {
        Plugin plugin = pluginService.getById(request.getPluginId());
        String baseUrl = plugin.getBaseUrl();
        CanObserveResponseDto response = pluginClient.canObserve(baseUrl, request.getResourceDescription());
        if (!response.isObservable()) {
            throw new ResourceDescriptionParseException(request.getResourceDescription(), request.getPluginId());
        }
        WebResourcePluginDto webResourcePluginDto = response.getWebResource();
        Optional<WebResource> optionalWebResource = webResourceService.findByNameAndPluginId(webResourcePluginDto.getName(), request.getPluginId());
        boolean isNotObserved = checkIsNotObserved(optionalWebResource);
        if (isNotObserved) {
            webResourcePluginDto = pluginClient.addResourceForObserving(baseUrl, request.getResourceDescription());
        }
        return webResourceObservingService.addObservingCascade(webResourcePluginDto, request);
    }

    @Override
    public WebResourceObservingDto stopWebResourceObserving(StopWebResourceObservingRequestDto request) {
        boolean isNeedChangeStatus = webResourceObservingService.stopObservingCascade(request.getWebResourceObservingId());
        if (isNeedChangeStatus) {
            WebResourceObserving observing = webResourceObservingService.getById(request.getWebResourceObservingId());
            Plugin plugin = observing.getWebResource().getPlugin();
            pluginClient.removeResourceFromObserve(plugin.getBaseUrl(), observing.getWebResource().getName());
        }
        WebResourceObserving observing = webResourceObservingService.getById(request.getWebResourceObservingId());
        return observingMapper.mapToDto(observing);
    }

    @Override
    public boolean checkAccess(UUID userId, UUID webResourceObservingId) {
        WebResourceObserving observing = webResourceObservingService.getById(webResourceObservingId);
        return observing.getUser().getId().equals(userId);
    }

    private boolean checkIsNotObserved(Optional<WebResource> optionalWebResource) {
        return optionalWebResource
                .map(webResource -> ObserveStatus.NOT_OBSERVE == webResource.getStatus())
                .orElse(true);
    }
}
