package com.victor.kochnev.core.service.webresourceobserving;

import com.victor.kochnev.core.base.BaseCoreUnitTest;
import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequest;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequestBuilder;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import com.victor.kochnev.domain.entity.builder.WebResourceBuilder;
import com.victor.kochnev.domain.entity.builder.WebResourceObservingBuilder;
import com.victor.kochnev.domain.value.object.ObserveSettings;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WebResourceObservingServiceImplUnitTest extends BaseCoreUnitTest {
    @InjectMocks
    private WebResourceObservingServiceImpl webResourceObservingService;

    @Test
    void testUpdateOrCreate_Create() {
        //Assign
        WebResource webResource = WebResourceBuilder.persistedDefaultBuilder().build();
        AddWebResourceForObservingRequest request = AddWebResourceForObservingRequestBuilder.defaultBuilder().build();
        when(webResourceObservingRepository.findByWebResourceIdAndUserId(webResource.getId(), request.getUserId()))
                .thenReturn(Optional.empty());
        when(webResourceObservingRepository.create(any()))
                .then(answer -> answer.getArguments()[0]);

        //Action
        WebResourceObservingDto webResourceObservingDto = webResourceObservingService.updateOrCreate(webResource, request);

        //Assert
        assertEquals(request.getObserveSettings(), webResourceObservingDto.getObserveSettings());
        assertEquals(webResource.getName(), webResourceObservingDto.getWebResourceDto().getName());
        verify(webResourceObservingRepository, times(1)).create(any());
    }

    @Test
    void testUpdateOrCreate_Update() {
        //Assign
        WebResource webResource = WebResourceBuilder.persistedDefaultBuilder().build();
        AddWebResourceForObservingRequest request = AddWebResourceForObservingRequestBuilder.defaultBuilder()
                .observeSettings(Optional.of(new ObserveSettings("email", "telegram")))
                .build();
        WebResourceObserving webResourceObserving = WebResourceObservingBuilder.persistedDefaultBuilder()
                .observeSettings(Optional.of(new ObserveSettings("testEmail", "testTelegram")))
                .build();
        when(webResourceObservingRepository.findByWebResourceIdAndUserId(webResource.getId(), request.getUserId()))
                .thenReturn(Optional.of(webResourceObserving));

        //Action
        WebResourceObservingDto webResourceObservingDto = webResourceObservingService.updateOrCreate(webResource, request);

        //Assert
        assertEquals(request.getObserveSettings(), webResourceObservingDto.getObserveSettings());
        assertEquals(webResource.getName(), webResourceObservingDto.getWebResourceDto().getName());
        verify(webResourceObservingRepository, times(0)).create(any());
    }
}
