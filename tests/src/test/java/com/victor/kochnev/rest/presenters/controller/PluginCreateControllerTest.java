package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.api.dto.*;
import com.victor.kochnev.dal.entity.*;
import com.victor.kochnev.domain.entity.builder.PluginDomainBuilder;
import com.victor.kochnev.domain.enums.PluginStatus;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;

class PluginCreateControllerTest extends BaseControllerTest {
    private final String PLUGIN_ENDPOINT = "/plugin";

    @Test
    void addWebResourceObserving_WhenUserAndPluginExists() {
        //Assign
        prepareDb();
        var requestBody = prepareAddRequest();

        //Action
        MvcResult mvcResult = post(PLUGIN_ENDPOINT, requestBody, prepareUserHeaders());

        //Assert
        assertHttpStatusOk(mvcResult);

        PluginEntity pluginEntity = pluginRepository.findByName(requestBody.getName()).get();
        assertEquals(requestBody.getName(), pluginEntity.getName());
        assertEquals(requestBody.getBaseUrl(), pluginEntity.getBaseUrl());
        assertEquals(PluginStatus.CREATED, pluginEntity.getStatus());

        Plugin plugin = getResponseDto(mvcResult, Plugin.class);
        assertNotNull(plugin);
        assertEquals(pluginEntity.getId(), plugin.getId());
        assertEquals(requestBody.getName(), plugin.getName());
        assertEquals(requestBody.getBaseUrl(), plugin.getBaseUrl());
    }

    private AddPluginRequestBody prepareAddRequest() {
        return new AddPluginRequestBody()
                .baseUrl(PluginDomainBuilder.DEFAULT_BASE_URL)
                .name(PluginDomainBuilder.DEFAULT_NAME);
    }

    private void prepareDb() {
        userRepository.save(UserEntityBuilder.defaultBuilder().build()).getId();
    }
}
