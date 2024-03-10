package com.victor.kochnev.core.service.webresource;

import com.victor.kochnev.core.BaseCoreUnitTest;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDtoBuilder;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.entity.builder.WebResourceDomainBuilder;
import com.victor.kochnev.domain.enums.ObserveStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WebResourceServiceImplUnitTest extends BaseCoreUnitTest {
    @InjectMocks
    private WebResourceServiceImpl webResourceService;

    @Test
    @Disabled
    void testUpdateOrCreate_Create() {
        //Assign
        UUID pluginId = UUID.randomUUID();
        String name = WebResourcePluginDtoBuilder.DEFAULT_NAME;
        when(webResourceRepository.findByNameAndPluginId(name, pluginId)).thenReturn(Optional.empty());
        WebResourcePluginDto webResourcePluginDto = WebResourcePluginDtoBuilder.defaultBuilder().build();

        //Action
        WebResource webResource = webResourceService.updateOrCreate(pluginId, webResourcePluginDto, ObserveStatus.NOT_OBSERVE);

        //Assert
        assertEquals(webResourcePluginDto.getName(), webResource.getName());
        assertEquals(webResourcePluginDto.getDescription(), webResource.getDescription());
        verify(webResourceRepository, times(1)).create(any());
    }

    @Test
    @Disabled
    void testUpdateOrCreate_Update() {
        //Assign
        UUID pluginId = UUID.randomUUID();
        String name = WebResourcePluginDtoBuilder.DEFAULT_NAME;
        WebResource webResource = WebResourceDomainBuilder.persistedDefaultBuilder()
                .name(name)
                .description(WebResourcePluginDtoBuilder.DEFAULT_DESCRIPTION)
                .build();
        when(webResourceRepository.findByNameAndPluginId(name, pluginId)).thenReturn(Optional.of(webResource));
        WebResourcePluginDto webResourcePluginDto = WebResourcePluginDtoBuilder.defaultBuilder().build();

        //Action
        WebResource actualWebResource = webResourceService.updateOrCreate(pluginId, webResourcePluginDto, ObserveStatus.NOT_OBSERVE);

        //Assert
        assertEquals(webResourcePluginDto.getName(), actualWebResource.getName());
        assertEquals(webResourcePluginDto.getDescription(), actualWebResource.getDescription());
        verify(webResourceRepository, times(0)).create(any());
    }
}
