package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.WebResourceRepository;
import com.victor.kochnev.dal.BaseBootTest;
import com.victor.kochnev.dal.entity.PluginEntityBuilder;
import com.victor.kochnev.dal.entity.WebResourceEntityBuilder;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.entity.builder.PluginDomainBuilder;
import com.victor.kochnev.domain.entity.builder.WebResourceDomainBuilder;
import com.victor.kochnev.domain.enums.ObserveStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WebResourceRepositoryTest extends BaseBootTest {
    @Autowired
    private WebResourceRepository webResourceRepository;

    @Test
    void testSuccessFindById() {
        //Assign
        UUID pluginId = pluginEntityRepository.save(PluginEntityBuilder.persistedDefaultBuilder().build()).getId();
        UUID webResourceId = webResourceEntityRepository.save(WebResourceEntityBuilder.persistedDefaultBuilder()
                .plugin(pluginEntityRepository.findById(pluginId).get()).build()).getId();

        //Action
        WebResource webResource = webResourceRepository.findById(webResourceId);

        //Assert
        assertEquals(webResourceId, webResource.getId());
        assertEquals(WebResourceEntityBuilder.DEFAULT_NAME, webResource.getName());
        assertEquals(WebResourceEntityBuilder.DEFAULT_DESCRIPTION, webResource.getDescription());
        assertEquals(WebResourceEntityBuilder.DEFAULT_STATUS, webResource.getStatus());

        assertNotNull(webResource.getPlugin());
    }

    @Test
    void testFindById_WhenNotExist_ExpectResourceNotFoundException() {
        //Assign
        UUID webResourceId = UUID.randomUUID();

        //Action
        assertThrows(ResourceNotFoundException.class, () -> webResourceRepository.findById(webResourceId));
    }

    @Test
    void testSuccessFindByNameAndPluginId() {
        //Assign
        UUID pluginId = pluginEntityRepository.save(PluginEntityBuilder.persistedDefaultBuilder().build()).getId();
        String name = webResourceEntityRepository.save(WebResourceEntityBuilder.persistedDefaultBuilder()
                .plugin(pluginEntityRepository.findById(pluginId).get()).build()).getName();

        //Action
        Optional<WebResource> optionalWebResource = webResourceRepository.findByNameAndPluginId(name, pluginId);

        //Assert
        assertTrue(optionalWebResource.isPresent());
        WebResource webResource = optionalWebResource.get();

        assertEquals(WebResourceEntityBuilder.DEFAULT_NAME, webResource.getName());
        assertEquals(WebResourceEntityBuilder.DEFAULT_DESCRIPTION, webResource.getDescription());
        assertEquals(WebResourceEntityBuilder.DEFAULT_STATUS, webResource.getStatus());

        assertNotNull(webResource.getPlugin());
    }

    @Test
    void testFindByNameAndPluginId_WhenNotExist_ExpectEmptyOptional() {
        //Assign
        UUID pluginId = UUID.randomUUID();
        String name = "testName";

        //Action
        Optional<WebResource> optionalWebResource = webResourceRepository.findByNameAndPluginId(name, pluginId);

        //Assert
        assertTrue(optionalWebResource.isEmpty());
    }

    @Test
    void testSuccessCreate() {
        //Assign
        UUID pluginId = pluginEntityRepository.save(PluginEntityBuilder.persistedDefaultBuilder().build()).getId();
        Plugin plugin = PluginDomainBuilder.defaultPlugin().id(pluginId).build();
        WebResource webResourceForSave = WebResourceDomainBuilder.persistedDefaultBuilder().plugin(plugin).build();

        //Action
        WebResource savedWebResource = webResourceRepository.create(webResourceForSave);

        //Assert
        assertEquals(WebResourceDomainBuilder.DEFAULT_NAME, savedWebResource.getName());
        assertEquals(WebResourceDomainBuilder.DEFAULT_DESCRIPTION, savedWebResource.getDescription());
        assertEquals(WebResourceDomainBuilder.DEFAULT_STATUS, savedWebResource.getStatus());
        assertNotNull(savedWebResource.getPlugin());

        assertNotNull(savedWebResource.getPlugin());
    }

    @Test
    void testSuccessUpdate() {
        //Assign
        String newDescription = "testDescriptionExamlpe";
        UUID pluginId = pluginEntityRepository.save(PluginEntityBuilder.persistedDefaultBuilder().build()).getId();
        Plugin plugin = PluginDomainBuilder.defaultPlugin().id(pluginId).build();
        UUID webResourceId = webResourceEntityRepository.save(WebResourceEntityBuilder.persistedDefaultBuilder()
                .plugin(pluginEntityRepository.findById(pluginId).get())
                .status(ObserveStatus.NOT_OBSERVE)
                .description("sadfsadas")
                .build()).getId();
        WebResource webResourceForUpdate = WebResourceDomainBuilder.persistedDefaultBuilder()
                .id(webResourceId)
                .description(newDescription)
                .plugin(plugin).build();

        //Action
        WebResource savedWebResource = webResourceRepository.update(webResourceForUpdate);

        //Assert
        assertEquals(WebResourceDomainBuilder.DEFAULT_NAME, savedWebResource.getName());
        assertEquals(newDescription, savedWebResource.getDescription());
        assertEquals(WebResourceDomainBuilder.DEFAULT_STATUS, savedWebResource.getStatus());
        assertNotNull(savedWebResource.getPlugin());

        assertNotNull(savedWebResource.getPlugin());
    }
}
