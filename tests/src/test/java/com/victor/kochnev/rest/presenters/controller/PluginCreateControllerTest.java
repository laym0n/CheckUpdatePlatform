package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.api.dto.AddPluginRequest;
import com.victor.kochnev.api.dto.AddPluginResponse;
import com.victor.kochnev.api.dto.Plugin;
import com.victor.kochnev.dal.entity.PluginEntity;
import com.victor.kochnev.dal.entity.UserEntity;
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
    UserEntity userForRequest;

    @Test
    void successAddPlugin() {
        //Assign
        prepareDb();
        var requestBody = prepareAddRequest();

        //Action
        MvcResult mvcResult = post(PLUGIN_ENDPOINT, requestBody, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        PluginEntity pluginEntity = pluginRepository.findByName(requestBody.getName()).get();
        assertEquals(requestBody.getName(), pluginEntity.getName());
        assertEquals(requestBody.getBaseUrl(), pluginEntity.getBaseUrl());
        assertEquals(PluginStatus.CREATED, pluginEntity.getStatus());

        var responseBody = getResponseDto(mvcResult, AddPluginResponse.class);
        assertNotNull(responseBody);

        assertNotNull(responseBody.getAccessToken());
        assertTrue(passwordEncoder.matches(responseBody.getAccessToken(), pluginEntity.getAccessToken()));

        Plugin pluginResponseDto = responseBody.getPlugin();
        assertEquals(pluginEntity.getId(), pluginResponseDto.getId());
        assertEquals(requestBody.getName(), pluginResponseDto.getName());
        assertEquals(requestBody.getBaseUrl(), pluginResponseDto.getBaseUrl());
    }

    private AddPluginRequest prepareAddRequest() {
        return new AddPluginRequest()
                .baseUrl(PluginDomainBuilder.DEFAULT_BASE_URL)
                .name(PluginDomainBuilder.DEFAULT_NAME);
    }

    private void prepareDb() {
        userForRequest = userRepository.save(UserEntityBuilder.defaultBuilder().build());
    }
}
