package com.victor.kochnev.core.service.webresourceobserving;

import com.victor.kochnev.core.converter.DomainWebResourceObservingMapper;
import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequest;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.UserRepository;
import com.victor.kochnev.core.repository.WebResourceObservingRepository;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import com.victor.kochnev.domain.enums.ObserveStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebResourceObservingServiceImpl implements WebResourceObservingService {
    private final DomainWebResourceObservingMapper webResourceObservingMapper;
    private final WebResourceObservingRepository webResourceObservingRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public WebResourceObservingDto updateOrCreate(WebResource webResource, AddWebResourceForObservingRequest request) {
        Optional<WebResourceObserving> webResourceObservingOptional = webResourceObservingRepository.findByWebResourceIdAndUserId(webResource.getId(), request.getUserId());

        WebResourceObserving webResourceObserving = webResourceObservingOptional.orElseGet(() -> {
            WebResourceObserving newObserving = new WebResourceObserving();
            newObserving.setUser(userRepository.findById(request.getUserId()));
            newObserving.setWebResource(webResource);
            return newObserving;
        });
        webResourceObserving.setObserveSettings(request.getObserveSettings());
        webResourceObserving.setStatus(ObserveStatus.OBSERVE);
        if (webResourceObservingOptional.isEmpty()) {
            webResourceObserving = webResourceObservingRepository.create(webResourceObserving);
        }
        return webResourceObservingMapper.mapToDto(webResourceObserving);
    }

    @Override
    @Transactional
    public WebResourceObservingDto setStatusByUserIdAndWebResourceId(UUID userId, UUID webResourceId, ObserveStatus status) {
        Optional<WebResourceObserving> optionalWebResourceObserving = webResourceObservingRepository.findByWebResourceIdAndUserId(webResourceId, userId);
        WebResourceObserving webResourceObserving = optionalWebResourceObserving
                .orElseThrow(() -> ResourceNotFoundException.create(WebResourceObserving.class, userId + " " + webResourceId, "userId webResourceId"));
        webResourceObserving.setStatus(status);
        return webResourceObservingMapper.mapToDto(webResourceObserving);
    }
}
