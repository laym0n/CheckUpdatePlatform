package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.dal.BaseBootTest;
import com.victor.kochnev.dal.embeddable.object.EmbeddablePluginDescriptionBuilder;
import com.victor.kochnev.dal.embeddable.object.EmbeddableSpecificPluginDescriptionBuilder;
import com.victor.kochnev.dal.entity.PluginEntityBuilder;
import com.victor.kochnev.dal.entity.WebResourceEntityBuilder;
import com.victor.kochnev.dal.entity.value.object.TagsInfo;
import com.victor.kochnev.domain.entity.Plugin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PluginRepositoryTest extends BaseBootTest {
    @Autowired
    private PluginRepository pluginRepository;

    @Test
    void testSuccessFindById() {
        //Assign
        UUID pluginId = pluginEntityRepository.save(PluginEntityBuilder.defaultBuilder().build()).getId();

        //Action
        Plugin plugin = pluginRepository.getById(pluginId);

        //Assert
        assertNotNull(plugin);
        assertEquals(pluginId, plugin.getId());
        assertEquals(PluginEntityBuilder.DEFAULT_NAME, plugin.getName());
        assertEquals(PluginEntityBuilder.DEFAULT_BASE_URL, plugin.getBaseUrl());
        assertEquals(PluginEntityBuilder.DEFAULT_ACCESS_TOKEN, plugin.getAccessToken());
        assertEquals(EmbeddableSpecificPluginDescriptionBuilder.DEFAULT_IMAGE_PATHS, plugin.getDescription().getSpecificDescription().getImagePaths());
        assertEquals(EmbeddableSpecificPluginDescriptionBuilder.DEFAULT_DESCRIPTION, plugin.getDescription().getSpecificDescription().getDescription());
        assertEquals(EmbeddableSpecificPluginDescriptionBuilder.DEFAULT_TAGS, plugin.getDescription().getSpecificDescription().getTags());
        assertEquals(EmbeddablePluginDescriptionBuilder.DEFAULT_DISTRIBUTION_METHODS_COLLECTION, plugin.getDescription().getDistributionMethods());
        assertEquals(EmbeddablePluginDescriptionBuilder.DEFAULT_LOGO_PATH, plugin.getDescription().getLogoPath());
        assertNotNull(plugin.getOwnerUser());
    }

    @Test
    void testFindById_WhenNotExists_ExpectResourceNotFoundException() {
        //Assert
        UUID pluginId = UUID.randomUUID();

        //Action
        assertThrows(ResourceNotFoundException.class, () -> pluginRepository.getById(pluginId));
    }

    @Test
    void testSuccessFindByName() {
        //Assign
        String name = pluginEntityRepository.save(PluginEntityBuilder.defaultBuilder().build()).getName();

        //Action
        Optional<Plugin> optionalPlugin = pluginRepository.findByName(name);

        //Assert
        assertTrue(optionalPlugin.isPresent());
        Plugin plugin = optionalPlugin.get();

        assertNotNull(plugin);
        assertEquals(PluginEntityBuilder.DEFAULT_NAME, plugin.getName());
        assertEquals(PluginEntityBuilder.DEFAULT_BASE_URL, plugin.getBaseUrl());
        assertEquals(PluginEntityBuilder.DEFAULT_ACCESS_TOKEN, plugin.getAccessToken());
        assertEquals(EmbeddableSpecificPluginDescriptionBuilder.DEFAULT_IMAGE_PATHS, plugin.getDescription().getSpecificDescription().getImagePaths());
        assertEquals(EmbeddableSpecificPluginDescriptionBuilder.DEFAULT_DESCRIPTION, plugin.getDescription().getSpecificDescription().getDescription());
        assertEquals(EmbeddableSpecificPluginDescriptionBuilder.DEFAULT_TAGS, plugin.getDescription().getSpecificDescription().getTags());
        assertEquals(EmbeddablePluginDescriptionBuilder.DEFAULT_DISTRIBUTION_METHODS_COLLECTION, plugin.getDescription().getDistributionMethods());
        assertEquals(EmbeddablePluginDescriptionBuilder.DEFAULT_LOGO_PATH, plugin.getDescription().getLogoPath());
        assertNotNull(plugin.getOwnerUser());
    }

    @Test
    void testFindByName_WhenNotExists_ExpectResourceNotFoundException() {
        //Assert
        String name = UUID.randomUUID().toString();

        //Action
        Optional<Plugin> optionalPlugin = pluginRepository.findByName(name);

        //Assert
        assertTrue(optionalPlugin.isEmpty());
    }

    @Test
    void testSuccessFindByWebResourceId() {
        //Assign
        UUID pluginId = pluginEntityRepository.save(PluginEntityBuilder.defaultBuilder().build()).getId();
        UUID webResourceId = webResourceEntityRepository.save(WebResourceEntityBuilder.persistedDefaultBuilder()
                .plugin(pluginEntityRepository.findById(pluginId).get()).build()).getId();


        //Action
        Plugin plugin = pluginRepository.findByWebResourceId(webResourceId);

        //Assert
        assertNotNull(plugin);
        assertEquals(pluginId, plugin.getId());
        assertEquals(PluginEntityBuilder.DEFAULT_NAME, plugin.getName());
        assertEquals(PluginEntityBuilder.DEFAULT_BASE_URL, plugin.getBaseUrl());
        assertEquals(PluginEntityBuilder.DEFAULT_ACCESS_TOKEN, plugin.getAccessToken());
        assertEquals(EmbeddableSpecificPluginDescriptionBuilder.DEFAULT_IMAGE_PATHS, plugin.getDescription().getSpecificDescription().getImagePaths());
        assertEquals(EmbeddableSpecificPluginDescriptionBuilder.DEFAULT_DESCRIPTION, plugin.getDescription().getSpecificDescription().getDescription());
        assertEquals(EmbeddableSpecificPluginDescriptionBuilder.DEFAULT_TAGS, plugin.getDescription().getSpecificDescription().getTags());
        assertEquals(EmbeddablePluginDescriptionBuilder.DEFAULT_DISTRIBUTION_METHODS_COLLECTION, plugin.getDescription().getDistributionMethods());
        assertEquals(EmbeddablePluginDescriptionBuilder.DEFAULT_LOGO_PATH, plugin.getDescription().getLogoPath());
        assertNotNull(plugin.getOwnerUser());
    }

    @Test
    void testSuccessUpdate() {
        //Assign
        UUID pluginId = pluginEntityRepository.save(PluginEntityBuilder.defaultBuilder()
                .description(EmbeddablePluginDescriptionBuilder.defaultBuilder()
                        .specificDescription(EmbeddableSpecificPluginDescriptionBuilder.defaultBuilder()
                                .tags(new TagsInfo(List.of("tag1")))
                                .build())
                        .build()).build()).getId();

        Plugin pluginForUpdate = pluginRepository.findByName(PluginEntityBuilder.DEFAULT_NAME).get();
        List<String> newTags = List.of("tag2");
        pluginForUpdate.getDescription().getSpecificDescription().setTags(newTags);

        //Action
        Plugin actualResult = pluginRepository.update(pluginForUpdate);

        //Assert
        assertNotNull(actualResult);
        assertEquals(pluginId, actualResult.getId());
        assertEquals(newTags, actualResult.getDescription().getSpecificDescription().getTags());
    }

    @Test
    void testFindByWebResourceId_WhenNotExists_ExpectResourceNotFoundException() {
        //Assert
        UUID webResourceId = UUID.randomUUID();

        //Action
        assertThrows(ResourceNotFoundException.class, () -> pluginRepository.findByWebResourceId(webResourceId));
    }
}
