package com.victor.kochnev.core.facade.webresourceobserving;

import com.victor.kochnev.core.converter.DomainWebResourceObservingMapper;
import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.plugin.CanObserveResponseDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequestDto;
import com.victor.kochnev.core.dto.request.GetWebResourceObservingsRequestDto;
import com.victor.kochnev.core.dto.request.UpdateWebResourceObservingRequestDto;
import com.victor.kochnev.core.dto.response.GetWebResouceObservingsResponseDto;
import com.victor.kochnev.core.exception.ResourceDescriptionParseException;
import com.victor.kochnev.core.integration.PluginClient;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.core.security.service.user.SecurityUserService;
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
    private final SecurityUserService securityUserService;

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
        return webResourceObservingService.addOrUpdateObservingCascade(webResourcePluginDto, request);
    }

    @PreAuthorize("@authorizationService.verifyCurrentUserCanManageObserving(#request.getWebResourceObservingId())")
    @Override
    public WebResourceObservingDto updateWebResourceObserving(@P("request") UpdateWebResourceObservingRequestDto request) {
        var observing = webResourceObservingService.getById(request.getWebResourceObservingId());
        if (request.getStatus() == ObserveStatus.OBSERVE && observing.getStatus() == ObserveStatus.NOT_OBSERVE) {
            var webResource = observing.getWebResource();
            WebResourcePluginDto webResourcePluginDto = null;
            if (webResource.getStatus() != ObserveStatus.OBSERVE) {
                var plugin = webResource.getPlugin();
                webResourcePluginDto = pluginClient.continueResourceObserving(plugin.getBaseUrl(), plugin.getName());
            }
            observing = webResourceObservingService.continueObservingCascade(webResourcePluginDto, request.getWebResourceObservingId());
        } else if (request.getStatus() == ObserveStatus.NOT_OBSERVE && observing.getStatus() == ObserveStatus.OBSERVE) {
            boolean isNeedChangeStatus = webResourceObservingService.stopObservingCascade(request.getWebResourceObservingId());
            if (isNeedChangeStatus) {
                observing = webResourceObservingService.getById(request.getWebResourceObservingId());
                Plugin plugin = observing.getWebResource().getPlugin();
                pluginClient.removeResourceFromObserve(plugin.getBaseUrl(), observing.getWebResource().getName());
            }
            observing = webResourceObservingService.getById(request.getWebResourceObservingId());
        }
        return observingMapper.mapToDto(observing);
    }

    @Override
    public GetWebResouceObservingsResponseDto getByFilters(GetWebResourceObservingsRequestDto request) {
        UserSecurity currentUser = securityUserService.getCurrentUser();
        return webResourceObservingService.getByFiltersForUser(request, currentUser.getId());
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
