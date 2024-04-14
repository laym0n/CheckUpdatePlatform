package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.core.dto.domain.entity.PluginInfoDto;
import com.victor.kochnev.core.dto.request.GetPluginsRequestDto;
import com.victor.kochnev.core.dto.request.PluginsFilterDto;
import com.victor.kochnev.core.dto.response.GetPluginsResponseDto;
import com.victor.kochnev.dal.embeddable.object.EmbeddablePluginDescriptionBuilder;
import com.victor.kochnev.dal.embeddable.object.EmbeddableSpecificPluginDescription;
import com.victor.kochnev.dal.entity.PluginEntityBuilder;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import com.victor.kochnev.dal.entity.value.object.TagsInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetPluginsControllerTest extends BaseControllerTest {
    private static UUID OWNER_ID;
    private static UUID PLUGIN_ID1;
    private static UUID PLUGIN_ID2;
    private static UUID PLUGIN_ID3;
    private static UUID PLUGIN_ID4;
    private final String PLUGIN_ENDPOINT = "/plugin";

    @Autowired
    PasswordEncoder passwordEncoder;
    UserEntity userForRequest;

    @Test
    void successGetAll() {
        //Assign
        prepareDb();
        GetPluginsRequestDto requestBody = prepareRequest();

        //Action
        MvcResult mvcResult = get(PLUGIN_ENDPOINT, requestBody, null);

        //Assert
        assertHttpStatusOk(mvcResult);

        var response = getResponseDto(mvcResult, GetPluginsResponseDto.class);

        assertNotNull(response);
        assertNotNull(response.getPlugins());
        assertEquals(4, response.getPlugins().size());
        assertContains(response.getPlugins(), PLUGIN_ID1);
        assertContains(response.getPlugins(), PLUGIN_ID2);
        assertContains(response.getPlugins(), PLUGIN_ID3);
        assertContains(response.getPlugins(), PLUGIN_ID4);
    }

    @Test
    void successGetByName() {
        //Assign
        prepareDb();
        GetPluginsRequestDto requestBody = prepareRequest();
        requestBody.getFilters().setName("1");

        //Action
        MvcResult mvcResult = get(PLUGIN_ENDPOINT, requestBody, null);

        //Assert
        assertHttpStatusOk(mvcResult);

        var response = getResponseDto(mvcResult, GetPluginsResponseDto.class);

        assertNotNull(response);
        assertNotNull(response.getPlugins());
        assertEquals(2, response.getPlugins().size());
        assertContains(response.getPlugins(), PLUGIN_ID1);
        assertContains(response.getPlugins(), PLUGIN_ID3);
    }

    private GetPluginsRequestDto prepareRequest() {
        var requestDto = new GetPluginsRequestDto();
        requestDto.setFilters(new PluginsFilterDto());
        return requestDto;
    }

    private void assertContains(List<PluginInfoDto> plugins, UUID pluginId) {
        assertTrue(plugins.stream().anyMatch(plugin -> plugin.getId().equals(pluginId)));
    }

    private void assertPluginIndex(List<PluginInfoDto> plugins, UUID pluginId, int index) {
        assertEquals(pluginId, plugins.get(index).getId());
    }

    void prepareDb() {
        OWNER_ID = userRepository.save(UserEntityBuilder.defaultBuilder().build()).getId();

        PLUGIN_ID1 = pluginRepository.save(PluginEntityBuilder.persistedPostfixBuilder(1)
                .name("Name1")
                .ownerUser(userRepository.findById(OWNER_ID).get())
                .description(EmbeddablePluginDescriptionBuilder.defaultBuilder()
                        .specificDescription(EmbeddableSpecificPluginDescription.builder()
                                .tags(new TagsInfo(
                                        List.of("tag1", "tag3")
                                ))
                                .build())
                        .build())
                .build()).getId();
        PLUGIN_ID2 = pluginRepository.save(PluginEntityBuilder.persistedPostfixBuilder(2)
                .name("Name2")
                .ownerUser(userRepository.findById(OWNER_ID).get())
                .description(EmbeddablePluginDescriptionBuilder.defaultBuilder()
                        .specificDescription(EmbeddableSpecificPluginDescription.builder()
                                .tags(new TagsInfo(
                                        List.of("tag1", "tag3", "tag2")
                                ))
                                .build())
                        .build())
                .build()).getId();
        PLUGIN_ID3 = pluginRepository.save(PluginEntityBuilder.persistedPostfixBuilder(3)
                .name("ИМЯ1")
                .ownerUser(userRepository.findById(OWNER_ID).get())
                .description(EmbeddablePluginDescriptionBuilder.defaultBuilder()
                        .specificDescription(EmbeddableSpecificPluginDescription.builder()
                                .tags(new TagsInfo(
                                        List.of()
                                ))
                                .build())
                        .build())
                .build()).getId();
        PLUGIN_ID4 = pluginRepository.save(PluginEntityBuilder.persistedPostfixBuilder(4)
                .name("Имя2")
                .ownerUser(userRepository.findById(OWNER_ID).get())
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
