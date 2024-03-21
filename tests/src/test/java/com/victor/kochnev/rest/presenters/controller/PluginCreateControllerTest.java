package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.api.dto.AddPluginRequestBody;
import com.victor.kochnev.api.dto.AddPluginResponseBody;
import com.victor.kochnev.api.dto.Plugin;
import com.victor.kochnev.dal.entity.PluginEntity;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import com.victor.kochnev.domain.entity.builder.PluginDomainBuilder;
import com.victor.kochnev.domain.enums.PluginStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;

class PluginCreateControllerTest extends BaseControllerTest {
    private final String PLUGIN_ENDPOINT = "/plugin";

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void successAddPlugin() {
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

        var responseBody = getResponseDto(mvcResult, AddPluginResponseBody.class);
        assertNotNull(responseBody);

        assertNotNull(responseBody.getAccessToken());
        assertTrue(passwordEncoder.matches(responseBody.getAccessToken(), pluginEntity.getAccessToken()));

        Plugin pluginResponseDto = responseBody.getPlugin();
        assertEquals(pluginEntity.getId(), pluginResponseDto.getId());
        assertEquals(requestBody.getName(), pluginResponseDto.getName());
        assertEquals(requestBody.getBaseUrl(), pluginResponseDto.getBaseUrl());
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
