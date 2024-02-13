package com.victor.kochnev.core.service.webresourceobserving;

import com.victor.kochnev.core.converter.DomainWebResourceObservingMapper;
import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequest;
import com.victor.kochnev.core.repository.UserRepository;
import com.victor.kochnev.core.repository.WebResourceObservingRepository;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import com.victor.kochnev.domain.value.object.ObserveSettings;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

        WebResourceObserving webResourceObserving;
        if (webResourceObservingOptional.isPresent()) {
            webResourceObserving = webResourceObservingOptional.get();
            webResourceObserving.setObserveSettings(request.getObserveSettings());
        } else {
            webResourceObserving = new WebResourceObserving();
            webResourceObserving.setUser(userRepository.findById(request.getUserId()));
            webResourceObserving.setWebResource(webResource);
            Optional<ObserveSettings> observeSettings = request.getObserveSettings();
            webResourceObserving.setObserveSettings(observeSettings);
            webResourceObserving = webResourceObservingRepository.create(webResourceObserving);
        }
        return webResourceObservingMapper.mapToDto(webResourceObserving);
    }
}
