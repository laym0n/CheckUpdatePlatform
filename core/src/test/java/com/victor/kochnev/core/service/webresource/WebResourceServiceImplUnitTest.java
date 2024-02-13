package com.victor.kochnev.core.service.webresource;

import com.victor.kochnev.core.base.BaseCoreUnitTest;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDtoBuilder;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.entity.builder.WebResourceBuilder;
import com.victor.kochnev.domain.enums.WebResourceStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class WebResourceServiceImplUnitTest extends BaseCoreUnitTest {
    @InjectMocks
    private WebResourceServiceImpl webResourceService;

    @Test
    void testSetStatus() {
        //Assign
        WebResourceStatus expectedStatus = WebResourceStatus.OBSERVED;
        UUID webResourceId = UUID.randomUUID();
        WebResource webResource = WebResourceBuilder.persistedDefaultBuilder()
                .status(WebResourceStatus.NOT_OBSERVED)
                .build();
        when(webResourceRepository.findById(webResourceId)).thenReturn(webResource);

        //Action
        webResourceService.setStatus(expectedStatus, webResourceId);

        //Assert
        assertEquals(expectedStatus, webResource.getStatus());
    }

    @Test
    void testSetStatus_NotFindWebResource() {
        //Assign
        UUID webResourceId = UUID.randomUUID();
        when(webResourceRepository.findById(webResourceId)).thenThrow(ResourceNotFoundException.class);

        //Action
        assertThrows(ResourceNotFoundException.class, () -> webResourceService.setStatus(WebResourceStatus.NOT_OBSERVED, webResourceId));
    }

    @Test
    void testUpdateOrCreate_Create() {
        //Assign
        UUID pluginId = UUID.randomUUID();
        String name = WebResourcePluginDtoBuilder.DEFAULT_NAME;
        when(webResourceRepository.findByNameAndPluginId(name, pluginId)).thenReturn(Optional.empty());
        WebResourcePluginDto webResourcePluginDto = WebResourcePluginDtoBuilder.defaultBuilder().build();

        //Action
        WebResource webResource = webResourceService.updateOrCreate(pluginId, webResourcePluginDto);

        //Assert
        assertEquals(webResourcePluginDto.getName(), webResource.getName());
        assertEquals(webResourcePluginDto.getDescription(), webResource.getDescription());
        verify(webResourceRepository, times(1)).create(any());
    }

    @Test
    void testUpdateOrCreate_Update() {
        //Assign
        UUID pluginId = UUID.randomUUID();
        String name = WebResourcePluginDtoBuilder.DEFAULT_NAME;
        WebResource webResource = WebResourceBuilder.persistedDefaultBuilder()
                .name(name)
                .description(WebResourcePluginDtoBuilder.DEFAULT_DESCRIPTION)
                .build();
        when(webResourceRepository.findByNameAndPluginId(name, pluginId)).thenReturn(Optional.of(webResource));
        WebResourcePluginDto webResourcePluginDto = WebResourcePluginDtoBuilder.defaultBuilder().build();

        //Action
        WebResource actualWebResource = webResourceService.updateOrCreate(pluginId, webResourcePluginDto);

        //Assert
        assertEquals(webResourcePluginDto.getName(), actualWebResource.getName());
        assertEquals(webResourcePluginDto.getDescription(), actualWebResource.getDescription());
        verify(webResourceRepository, times(0)).create(any());
    }
}
