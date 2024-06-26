package com.victor.kochnev.core.service.webresourceobserving;

import com.victor.kochnev.core.converter.DomainWebResourceObservingMapper;
import com.victor.kochnev.core.converter.RequestDtoMapper;
import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequestDto;
import com.victor.kochnev.core.dto.request.GetWebResourceObservingsRequestDto;
import com.victor.kochnev.core.dto.response.GetWebResouceObservingsResponseDto;
import com.victor.kochnev.core.repository.UserRepository;
import com.victor.kochnev.core.repository.WebResourceObservingRepository;
import com.victor.kochnev.core.security.service.user.SecurityUserService;
import com.victor.kochnev.core.service.webresource.WebResourceService;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import com.victor.kochnev.domain.enums.ObserveStatus;
import com.victor.kochnev.domain.value.object.ObserveSettings;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebResourceObservingServiceImpl implements WebResourceObservingService {
    private final DomainWebResourceObservingMapper webResourceObservingMapper;
    private final WebResourceObservingRepository observingRepository;
    private final WebResourceService webResourceService;
    private final SecurityUserService securityUserService;
    private final UserRepository userRepository;
    private final RequestDtoMapper requestDtoMapper;

    @Override
    @Transactional
    public WebResourceObservingDto addOrUpdateObservingCascade(WebResourcePluginDto webResourcePluginDto, AddWebResourceForObservingRequestDto request) {
        UUID pluginId = request.getPluginId();
        UUID userId = securityUserService.getCurrentUser().getId();
        WebResource webResource = webResourceService.updateOrCreate(pluginId, webResourcePluginDto, ObserveStatus.OBSERVE);
        UUID webResourceId = webResource.getId();
        Optional<WebResourceObserving> webResourceObservingOptional = observingRepository.findByWebResourceIdAndUserId(webResourceId, userId);

        WebResourceObserving webResourceObserving = webResourceObservingOptional.orElseGet(() -> {
            WebResourceObserving newObserving = new WebResourceObserving();
            newObserving.setUser(userRepository.getById(userId));
            newObserving.setWebResource(webResource);
            newObserving.setObserveSettings(new ObserveSettings(true));
            return newObserving;
        });
        webResourceObserving.setStatus(ObserveStatus.OBSERVE);
        if (webResourceObservingOptional.isEmpty()) {
            webResourceObserving = observingRepository.create(webResourceObserving);
        } else {
            webResourceObserving = observingRepository.update(webResourceObserving);
        }
        return webResourceObservingMapper.mapToDto(webResourceObserving);
    }

    @Override
    @Transactional
    public WebResourceObserving continueObservingCascade(WebResourcePluginDto webResourcePluginDto, UUID observingId) {
        var observing = observingRepository.getById(observingId);
        if (webResourcePluginDto != null) {
            observing.setWebResource(webResourceService.updateOrCreate(observing.getWebResource().getPlugin().getId(), webResourcePluginDto, ObserveStatus.OBSERVE));
        }
        observing.setStatus(ObserveStatus.OBSERVE);
        return observingRepository.update(observing);
    }

    @Override
    @Transactional
    public boolean stopObservingCascade(UUID observingId) {
        WebResourceObserving observing = getById(observingId);
        observing.setStatus(ObserveStatus.NOT_OBSERVE);
        WebResource webResource = observing.getWebResource();
        boolean isChangedCascade = false;
        if (ObserveStatus.OBSERVE == webResource.getStatus()) {
            isChangedCascade = stopObserveCascade(webResource);
        }
        observingRepository.update(observing);
        return isChangedCascade;
    }

    @Override
    public GetWebResouceObservingsResponseDto getByFiltersForUser(GetWebResourceObservingsRequestDto request, UUID userId) {
        var dalRequestDto = requestDtoMapper.mapToDal(request);
        dalRequestDto.getFilters().setUserIds(List.of(userId));
        var dalResponseDto = observingRepository.getByFilters(dalRequestDto);
        return webResourceObservingMapper.mapToDto(dalResponseDto);
    }

    @Override
    @Transactional
    public List<WebResourceObserving> findAllActualObservings(String name) {
        return observingRepository.findAllWithExpiredDateAfterOrNull(name, ZonedDateTime.now())
                .stream().filter(observing -> observing.getStatus() == ObserveStatus.OBSERVE)
                .toList();
    }

    @Override
    @Transactional
    public WebResourceObserving getById(UUID observingId) {
        return observingRepository.getById(observingId);
    }

    private boolean stopObserveCascade(WebResource webResource) {
        boolean isChangedCascade = false;
        int countObservers = observingRepository.countObserversWithStatus(webResource.getId(), ObserveStatus.OBSERVE);
        log.info("COUNT ACTUAL OBSERVERS {}", countObservers);
        if (countObservers == 1) {
            webResource.setStatus(ObserveStatus.NOT_OBSERVE);
            webResourceService.setStatus(ObserveStatus.NOT_OBSERVE, webResource.getId());
            isChangedCascade = true;
        }
        return isChangedCascade;
    }
}
