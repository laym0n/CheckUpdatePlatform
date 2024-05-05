package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.core.dto.domain.entity.PluginDto;
import com.victor.kochnev.core.dto.request.UpdatePluginRequestDto;
import com.victor.kochnev.dal.entity.PluginEntity;
import com.victor.kochnev.dal.entity.PluginEntityBuilder;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PluginUpdateControllerTest extends BaseControllerTest {
    private final String UPDATE_ENDPOINT = "/plugin/update";
    @Autowired
    PasswordEncoder passwordEncoder;
    UserEntity userForRequest;
    private UUID PLUGIN_ID;

    @Test
    void successRefresh() {
        //Assign
        prepareDb();
        var requestDto = prepareRequest();

        //Action
        MvcResult mvcResult = put(UPDATE_ENDPOINT, requestDto, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        var pluginDto = getResponseDto(mvcResult, PluginDto.class);
        assertNotNull(pluginDto);
        assertEquals(requestDto.getName(), pluginDto.getName());
        assertEquals(requestDto.getBaseUrl(), pluginDto.getBaseUrl());

        PluginEntity pluginEntity = pluginRepository.findById(PLUGIN_ID).get();
        assertEquals(requestDto.getName(), pluginEntity.getName());
        assertEquals(requestDto.getBaseUrl(), pluginEntity.getBaseUrl());
    }

    @Test
    void refreshByNotOwner_Expect401() {
        //Assign
        prepareDb();
        userForRequest = userRepository.save(UserEntityBuilder.persistedPostfixBuilder(1).build());

        var requestDto = prepareRequest();
        PluginEntity initPlugin = pluginRepository.findById(PLUGIN_ID).get();

        //Action
        MvcResult mvcResult = put(UPDATE_ENDPOINT, requestDto, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatus(mvcResult, HttpStatus.UNAUTHORIZED);

        PluginEntity pluginEntity = pluginRepository.findById(PLUGIN_ID).get();
        assertEquals(initPlugin.getName(), pluginEntity.getName());
        assertEquals(initPlugin.getBaseUrl(), pluginEntity.getBaseUrl());
    }

    private UpdatePluginRequestDto prepareRequest() {
        var requestDto = new UpdatePluginRequestDto();
        requestDto.setPluginId(PLUGIN_ID);
        requestDto.setName("testName");
        requestDto.setBaseUrl("testBaseUrl");
        return requestDto;
    }

    private void prepareDb() {
        userForRequest = userRepository.save(UserEntityBuilder.defaultBuilder().build());
        PLUGIN_ID = pluginRepository.save(PluginEntityBuilder.persistedPostfixBuilder(1)
                .ownerUser(userRepository.findById(userForRequest.getId()).get())
                .build()).getId();
    }
}
