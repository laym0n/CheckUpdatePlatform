package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.dto.dal.GetPluginsDalRequestDto;
import com.victor.kochnev.core.dto.dal.PluginsFilterDalDto;
import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.dal.BaseBootTest;
import com.victor.kochnev.dal.embeddable.object.EmbeddablePluginDescriptionBuilder;
import com.victor.kochnev.dal.embeddable.object.EmbeddableSpecificPluginDescription;
import com.victor.kochnev.dal.entity.PluginEntityBuilder;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import com.victor.kochnev.dal.entity.value.object.TagsInfo;
import com.victor.kochnev.domain.entity.Plugin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetByFiltersPluginRepositoryTest extends BaseBootTest {
    private static UUID OWNER_ID;
    private static UUID PLUGIN_ID1;
    private static UUID PLUGIN_ID2;
    private static UUID PLUGIN_ID3;
    private static UUID PLUGIN_ID4;
    @Autowired
    private PluginRepository pluginRepository;

    @Test
    void testGetByFilters_GetAll() {
        //Assign
        prepareDb();
        GetPluginsDalRequestDto request = new GetPluginsDalRequestDto();

        //Action
        var response = pluginRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertNotNull(response.getPlugins());
        assertEquals(4, response.getPlugins().size());

        assertContains(response.getPlugins(), PLUGIN_ID1);
        assertContains(response.getPlugins(), PLUGIN_ID2);
        assertContains(response.getPlugins(), PLUGIN_ID3);
        assertContains(response.getPlugins(), PLUGIN_ID4);
    }

    @Test
    void testGetByFilters_GetByName_GetAll() {
        //Assign
        prepareDb();
        var request = prepareRequest();
        request.getFilters().setName("1");

        //Action
        var response = pluginRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertNotNull(response.getPlugins());
        assertEquals(2, response.getPlugins().size());

        assertContains(response.getPlugins(), PLUGIN_ID1);
        assertContains(response.getPlugins(), PLUGIN_ID3);
    }

    @Test
    void testGetByFilters_GetByName_GetHalf() {
        //Assign
        prepareDb();
        var request = prepareRequest();
        request.getFilters().setName("м");

        //Action
        var response = pluginRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertNotNull(response.getPlugins());
        assertEquals(2, response.getPlugins().size());

        assertContains(response.getPlugins(), PLUGIN_ID3);
        assertContains(response.getPlugins(), PLUGIN_ID4);
    }

    @Test
    void testGetByFilters_GetByTags() {
        //Assign
        prepareDb();
        var request = prepareRequest();
        request.getFilters().setTags(List.of("tag1", "tag2", "tag3"));

        //Action
        var response = pluginRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertNotNull(response.getPlugins());
        assertEquals(4, response.getPlugins().size());

        assertPluginIndex(response.getPlugins(), PLUGIN_ID2, 0);
        assertPluginIndex(response.getPlugins(), PLUGIN_ID1, 1);
        assertPluginIndex(response.getPlugins(), PLUGIN_ID4, 2);
        assertPluginIndex(response.getPlugins(), PLUGIN_ID3, 3);
    }

    @Test
    void testGetByFilters_GetById() {
        //Assign
        prepareDb();
        var request = prepareRequest();
        request.getFilters().setIds(List.of(PLUGIN_ID1, PLUGIN_ID4));

        //Action
        var response = pluginRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertNotNull(response.getPlugins());
        assertEquals(2, response.getPlugins().size());

        assertContains(response.getPlugins(), PLUGIN_ID1);
        assertContains(response.getPlugins(), PLUGIN_ID4);
    }

    private GetPluginsDalRequestDto prepareRequest() {
        var requestDto = new GetPluginsDalRequestDto();
        requestDto.setFilters(new PluginsFilterDalDto());
        return requestDto;
    }

    private void assertContains(List<Plugin> plugins, UUID pluginId) {
        assertTrue(plugins.stream().anyMatch(plugin -> plugin.getId().equals(pluginId)));
    }

    private void assertPluginIndex(List<Plugin> plugins, UUID pluginId, int index) {
        assertEquals(pluginId, plugins.get(index).getId());
    }

    void prepareDb() {
        OWNER_ID = userEntityRepository.save(UserEntityBuilder.defaultBuilder().build()).getId();

        PLUGIN_ID1 = pluginEntityRepository.save(PluginEntityBuilder.persistedPostfixBuilder(1)
                .name("Name1")
                .ownerUser(userEntityRepository.findById(OWNER_ID).get())
                .description(EmbeddablePluginDescriptionBuilder.defaultBuilder()
                        .specificDescription(EmbeddableSpecificPluginDescription.builder()
                                .tags(new TagsInfo(
                                        List.of("tag1", "tag3")
                                ))
                                .build())
                        .build())
                .build()).getId();
        PLUGIN_ID2 = pluginEntityRepository.save(PluginEntityBuilder.persistedPostfixBuilder(2)
                .name("Name2")
                .ownerUser(userEntityRepository.findById(OWNER_ID).get())
                .description(EmbeddablePluginDescriptionBuilder.defaultBuilder()
                        .specificDescription(EmbeddableSpecificPluginDescription.builder()
                                .tags(new TagsInfo(
                                        List.of("tag1", "tag3", "tag2")
                                ))
                                .build())
                        .build())
                .build()).getId();
        PLUGIN_ID3 = pluginEntityRepository.save(PluginEntityBuilder.persistedPostfixBuilder(3)
                .name("ИМЯ1")
                .ownerUser(userEntityRepository.findById(OWNER_ID).get())
                .description(EmbeddablePluginDescriptionBuilder.defaultBuilder()
                        .specificDescription(EmbeddableSpecificPluginDescription.builder()
                                .tags(new TagsInfo(
                                        List.of()
                                ))
                                .build())
                        .build())
                .build()).getId();
        PLUGIN_ID4 = pluginEntityRepository.save(PluginEntityBuilder.persistedPostfixBuilder(4)
                .name("Имя2")
                .ownerUser(userEntityRepository.findById(OWNER_ID).get())
                .description(EmbeddablePluginDescriptionBuilder.defaultBuilder()
                        .specificDescription(EmbeddableSpecificPluginDescription.builder()
                                .tags(new TagsInfo(
                                        List.of("tag2")
                                ))
                                .build())
                        .build())
                .build()).getId();
    }
}
