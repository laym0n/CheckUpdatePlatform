package com.victor.kochnev.core.service.webresourceobserving;

import com.victor.kochnev.core.base.BaseCoreTest;
import com.victor.kochnev.core.dto.request.AddWebResourceForObservingRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.UUID;

class WebResourceObservingServiceImplTest extends BaseCoreTest {
    @InjectMocks
    WebResourceObservingServiceImpl webResourceService;

    @Test
    void test() {
        //Assign
        AddWebResourceForObservingRequest request = AddWebResourceForObservingRequest.builder()
                .userId(UUID.randomUUID())
                .pluginId(UUID.randomUUID())
                .resourceDescription("description")
                .build();

        //Action
//        WebResourceDto webResourceDto = webResourceService.addWebResourceForObserving(webResource, request);

        //Assert
    }
}
